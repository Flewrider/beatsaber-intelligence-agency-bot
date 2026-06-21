package bot.main.beatleader;

import bot.api.NodeBackend;
import bot.db.DatabaseManager;
import bot.dto.clan.ClanPlayer;
import bot.dto.player.DataBasePlayer;
import bot.main.BotConstants;
import bot.utils.DiscordLogger;
import bot.utils.Format;
import bot.utils.Messages;
import java.awt.Color;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class ClanPlayersWatcher {
    private final NodeBackend backend = new NodeBackend();
    private final JDA jda;
    private final DatabaseManager db;
    private final String clanId;
    private List<ClanPlayer> lastCheckedClanPlayers;
    List<DataBasePlayer> dbPlayers;
    TextChannel bsgClanChannel;

    public ClanPlayersWatcher(String clanId, DatabaseManager db, JDA jda) {
        this.jda = jda;
        this.db = db;
        this.clanId = clanId;
        this.lastCheckedClanPlayers = null;
        this.dbPlayers = new ArrayList<DataBasePlayer>();
        this.bsgClanChannel = jda.getTextChannelById(BotConstants.bsgClanChannelId);
    }

    public void startWatching() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::checkForNewClanPlayers, 0L, 15L, TimeUnit.MINUTES);
    }

    private void checkForNewClanPlayers() {
        DiscordLogger.sendLogInChannel("\ud83d\udfe1 Starting Clan Role Refresh... [" + Format.oneDigitZero(LocalTime.now().getHour()) + ":" + Format.oneDigitZero(LocalTime.now().getMinute()) + "]", "foaa-refresh");
        this.db.connectToDatabase();
        this.dbPlayers = this.db.getAllStoredPlayers();
        try {
            String profileUrl;
            List<ClanPlayer> currentClanPlayers = this.backend.getClanPlayers(this.clanId);
            if (currentClanPlayers == null) {
                DiscordLogger.sendLogInChannel("\ud83d\udd34 Error while trying to get current BL clan members!", "http-errors");
                return;
            }
            if (this.lastCheckedClanPlayers == null) {
                for (ClanPlayer player2 : currentClanPlayers) {
                    this.assignDiscordRole(player2);
                }
                this.lastCheckedClanPlayers = currentClanPlayers;
                DiscordLogger.sendLogInChannel("\ud83d\udfe2 Initial Clan Role Refresh Finished!", "foaa-refresh");
                return;
            }
            List<ClanPlayer> newPlayers = currentClanPlayers.stream().filter(player -> this.lastCheckedClanPlayers.stream().noneMatch(storedPlayer -> storedPlayer.getId().equals(player.getId()))).collect(Collectors.toList());
            List<ClanPlayer> leftPlayers = this.lastCheckedClanPlayers.stream().filter(storedPlayer -> currentClanPlayers.stream().noneMatch(player -> player.getId().equals(storedPlayer.getId()))).collect(Collectors.toList());
            for (ClanPlayer newPlayer : newPlayers) {
                String welcomeTitle = ":speaking_head: New Member Alert! :speaking_head:";
                String welcomeMessage = String.format("Welcome %s to the BSG Clan!\nGlad to have you on board.\n:black_heart: :heart: :yellow_heart:", Format.bold(newPlayer.getName()));
                profileUrl = "https://www.beatleader.xyz/u/" + newPlayer.getId();
                Color welcomeColor = Color.CYAN;
                Messages.sendBsgRankMessage(welcomeTitle, welcomeMessage, profileUrl, welcomeColor, newPlayer.getAvatar(), (MessageChannel)this.bsgClanChannel);
                this.assignDiscordRole(newPlayer);
            }
            for (ClanPlayer leftPlayer : leftPlayers) {
                String farewellTitle = "Member Departure \ud83d\udc4b\ud83c\udffb";
                String farewellMessage = String.format("%s has left the BSG Clan.\nBest wishes!", Format.bold(leftPlayer.getName()));
                profileUrl = "https://www.beatleader.xyz/u/" + leftPlayer.getId();
                Color farewellColor = Color.ORANGE;
                Messages.sendBsgRankMessage(farewellTitle, farewellMessage, profileUrl, farewellColor, leftPlayer.getAvatar(), (MessageChannel)this.bsgClanChannel);
                this.removeDiscordRole(leftPlayer);
            }
            this.lastCheckedClanPlayers = currentClanPlayers;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        DiscordLogger.sendLogInChannel("\ud83d\udfe2 Clan Role Refresh Finished!", "foaa-refresh");
    }

    private void assignDiscordRole(ClanPlayer player) {
        Member member = this.findMember(player.getId());
        if (member != null) {
            Role roleToAssign = (Role)member.getGuild().getRolesByName("clan member", true).get(0);
            member.getGuild().addRoleToMember(member, roleToAssign).queue();
        }
    }

    private void removeDiscordRole(ClanPlayer player) {
        Member member = this.findMember(player.getId());
        if (member != null) {
            Role roleToRemove = (Role)member.getGuild().getRolesByName("clan member", true).get(0);
            member.getGuild().removeRoleFromMember(member, roleToRemove).queue();
        }
    }

    private Member findMember(String playerId) {
        DataBasePlayer dbPlayer = this.dbPlayers.stream().filter(player -> playerId.equals(player.getId())).findFirst().orElse(null);
        if (dbPlayer == null) {
            return null;
        }
        long discordId = dbPlayer.getDiscordUserId();
        Guild bsgGuild = this.jda.getGuildById(BotConstants.bsgServerId);
        if (bsgGuild != null) {
            return bsgGuild.getMemberById(discordId);
        }
        return null;
    }
}

