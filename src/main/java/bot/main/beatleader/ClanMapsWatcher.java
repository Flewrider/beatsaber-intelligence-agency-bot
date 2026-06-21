package bot.main.beatleader;

import bot.api.BeatLeader;
import bot.api.NodeBackend;
import bot.db.DatabaseManager;
import bot.dto.clan.ClanMap;
import bot.dto.clan.LeaderboardInfo;
import bot.main.BotConstants;
import bot.utils.DiscordLogger;
import bot.utils.Format;
import bot.utils.Messages;
import java.awt.Color;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class ClanMapsWatcher {
    private final NodeBackend backend = new NodeBackend();
    private final BeatLeader bl = new BeatLeader();
    private final DatabaseManager dbManager;
    private final String clanId;
    private List<ClanMap> lastCheckedClanMaps;
    TextChannel bsgMapsChannel;

    public ClanMapsWatcher(String clanId, DatabaseManager dbManager, JDA jda) {
        this.dbManager = dbManager;
        this.clanId = clanId;
        this.lastCheckedClanMaps = null;
        this.bsgMapsChannel = jda.getTextChannelById(BotConstants.bsgClanChannelId);
    }

    public void startWatching() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::checkForMapChanges, 0L, 15L, TimeUnit.MINUTES);
    }

    private void checkForMapChanges() {
        DiscordLogger.sendLogInChannel("\ud83d\udfe1 Starting Clan Maps Watcher... [" + Format.oneDigitZero(LocalTime.now().getHour()) + ":" + Format.oneDigitZero(LocalTime.now().getMinute()) + "]", "foaa-refresh");
        try {
            List<ClanMap> currentClanMaps = this.backend.getClanMaps(this.clanId, "tohold", 100);
            if (currentClanMaps == null) {
                DiscordLogger.sendLogInChannel("\ud83d\udd34 Error while trying to get current clan maps!", "http-errors");
                return;
            }
            if (this.lastCheckedClanMaps == null) {
                this.lastCheckedClanMaps = currentClanMaps;
                DiscordLogger.sendLogInChannel("\ud83d\udfe2 Initial Clan Maps Watcher Finished!", "foaa-refresh");
                return;
            }
            List<ClanMap> newCaptures = currentClanMaps.stream().filter(map -> this.lastCheckedClanMaps.stream().noneMatch(storedMap -> storedMap.getLeaderboardId().equals(map.getLeaderboardId()))).collect(Collectors.toList());
            List<ClanMap> lostMaps = this.lastCheckedClanMaps.stream().filter(storedMap -> currentClanMaps.stream().noneMatch(map -> map.getLeaderboardId().equals(storedMap.getLeaderboardId()))).collect(Collectors.toList());
            for (ClanMap newCapture : newCaptures) {
                LeaderboardInfo leaderboardInfo = this.bl.getLeaderboardInfo(newCapture.getLeaderboardId());
                LeaderboardInfo.Score.PlayerInfo capturingPlayer = null;
                if (leaderboardInfo != null && leaderboardInfo.getScores() != null) {
                    capturingPlayer = this.findCapturingPlayer(leaderboardInfo);
                }
                this.sendMapNotification(newCapture, true, null, capturingPlayer);
            }
            for (ClanMap lostMap : lostMaps) {
                this.processLostMap(lostMap);
            }
            this.lastCheckedClanMaps = currentClanMaps;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        DiscordLogger.sendLogInChannel("\ud83d\udfe2 Clan Maps Watcher Finished!", "foaa-refresh");
    }

    private void processLostMap(ClanMap lostMap) {
        LeaderboardInfo leaderboardInfo = this.bl.getLeaderboardInfo(lostMap.getLeaderboardId());
        if (leaderboardInfo != null && leaderboardInfo.getClan() != null && !leaderboardInfo.getClan().getTag().equalsIgnoreCase(this.clanId)) {
            LeaderboardInfo.Score.PlayerInfo capturingPlayer = this.findCapturingPlayer(leaderboardInfo);
            this.sendMapNotification(lostMap, false, leaderboardInfo.getClan().getTag(), capturingPlayer);
        } else {
            this.sendMapNotification(lostMap, false, null, null);
        }
    }

    private void sendMapNotification(ClanMap map, boolean isNewCapture, String conqueringClanTag, LeaderboardInfo.Score.PlayerInfo player) {
        Color color;
        String message;
        String title;
        Object playerCaptureMessage = "";
        if (isNewCapture) {
            if (player != null) {
                long playerId = Long.parseLong(player.getPlayerId());
                this.dbManager.incrementCaptureCount(playerId);
                int captureCount = this.dbManager.getCaptureCount(playerId);
                String playerUrl = "https://www.beatleader.xyz/u/" + player.getPlayerId();
                playerCaptureMessage = "\nCaptured by **[" + player.getName() + "](" + playerUrl + ")**! :black_heart: :heart: :yellow_heart:\n" + Format.underline(player.getName() + " capture count:") + Format.bold(" " + captureCount);
            }
            title = "New Map Capture! :map:";
            message = String.format("**%s** has been captured on **%s** difficulty. Great job team! \ud83d\udc4f\n%s", map.getSongName(), map.getDifficultyName(), playerCaptureMessage);
            color = Color.GREEN;
        } else {
            if (player != null) {
                String playerUrl = "https://www.beatleader.xyz/u/" + player.getPlayerId();
                playerCaptureMessage = "\nStolen by **[" + player.getName() + "](" + playerUrl + ")**! ";
            }
            title = "Map Lost... :pensive::wilted_rose:";
            message = conqueringClanTag != null ? String.format("We've lost **%s** on **%s** difficulty to **%s** clan.%s\nTime to reclaim it!", map.getSongName(), map.getDifficultyName(), conqueringClanTag, playerCaptureMessage) : String.format("We've lost **%s** on **%s** difficulty.\nTime to reclaim it!", map.getSongName(), map.getDifficultyName());
            color = Color.RED;
        }
        Messages.sendBsgRankMessage(title, message, "https://www.beatleader.xyz/leaderboard/global/" + map.getLeaderboardId(), color, map.getSongCover(), (MessageChannel)this.bsgMapsChannel);
    }

    private LeaderboardInfo.Score.PlayerInfo findCapturingPlayer(LeaderboardInfo leaderboardInfo) {
        LocalDateTime twentyMinutesAgo = LocalDateTime.now(ZoneId.systemDefault()).minus(20L, ChronoUnit.MINUTES);
        return leaderboardInfo.getScores().stream().filter(score -> score.getPlayer() != null && score.getPlayer().getClans() != null).filter(score -> {
            LocalDateTime scoreTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(score.getTimeset())), ZoneId.systemDefault());
            return scoreTime.isAfter(twentyMinutesAgo);
        }).max(Comparator.comparing(score -> Long.parseLong(score.getTimeset()))).map(LeaderboardInfo.Score::getPlayer).orElse(null);
    }
}

