package bot.commands.beatleader;

import bot.api.BeatLeader;
import bot.api.NodeBackend;
import bot.commands.beatleader.PlaylistGenerator;
import bot.dto.MessageEventDTO;
import bot.dto.beatleader.ClanPlaylistFilter;
import bot.dto.beatleader.accgraph.PlayerBLRankedScore;
import bot.dto.clan.ClanMap;
import bot.dto.player.DataBasePlayer;
import bot.main.BotConstants;
import bot.utils.Format;
import bot.utils.Messages;
import java.io.File;
import java.util.List;
import net.dv8tion.jda.api.entities.MessageChannel;

public class ToConquer {
    NodeBackend backend = new NodeBackend();
    BeatLeader bl = new BeatLeader();

    public void sendToConquerPlaylist(int limit, ClanPlaylistFilter filter, DataBasePlayer player, MessageEventDTO event) {
        String tempMessage = filter == null ? "Fetching maps. Please wait..." : "Building playlist based on your filter, this may take some time...";
        int tempMessageDuration = filter == null ? 15 : 20;
        Messages.sendTempMessage(tempMessage, tempMessageDuration, (MessageChannel)event.getChannel());
        int maxPage = filter != null ? 20 : 10;
        List<ClanMap> maps = this.backend.getClanMaps("GER", "toconquer", maxPage);
        if (maps == null) {
            Messages.sendMessage("Could not fetch clan maps. Please try again later.", event);
            return;
        }
        StringBuilder titleDetails = new StringBuilder();
        StringBuilder filenameDetails = new StringBuilder();
        if (filter != null) {
            maps.removeIf(map -> map.getAccRating() > filter.getMaxAccRating() || map.getPassRating() > filter.getMaxPassRating() || map.getTechRating() > filter.getMaxTechRating());
            if (filter.isUnplayedOnly()) {
                List<PlayerBLRankedScore> playerRankedScores = this.bl.getAllPlayerRankedScores(player.getPlayerIdLong());
                if (playerRankedScores != null) {
                    maps.removeIf(map -> playerRankedScores.stream().anyMatch(s -> s.getLeaderboardId().equals(map.getLeaderboardId())));
                }
            }
            if (filter.getMaxAccRating() != 9999999.0f) {
                titleDetails.append(" Acc \u2264 ").append(filter.getMaxAccRating()).append(",");
                filenameDetails.append("_Acc").append(filter.getMaxAccRating());
            }
            if (filter.getMaxPassRating() != 9999999.0f) {
                titleDetails.append(" Pass \u2264 ").append(filter.getMaxPassRating()).append(",");
                filenameDetails.append("_Pass").append(filter.getMaxPassRating());
            }
            if (filter.getMaxTechRating() != 9999999.0f) {
                titleDetails.append(" Tech \u2264 ").append(filter.getMaxTechRating()).append(",");
                filenameDetails.append("_Tech").append(filter.getMaxTechRating());
            }
            if (filter.isUnplayedOnly()) {
                titleDetails.append(" Unplayed");
                filenameDetails.append("_Unplayed");
            }
        }
        if (maps.isEmpty()) {
            Messages.sendMessage("No maps found to conquer that match the filter criteria.", event);
            return;
        }
        if (maps.size() > limit) {
            maps = maps.subList(0, limit);
        }
        String baseTitle = filter == null ? "GER maps to conquer" : "ToConquer";
        String title = baseTitle + titleDetails;
        String clanIcon = filter == null ? BotConstants.playlistImageBsg : BotConstants.filterPlaylistImageBsg;
        String baseFilePath = BotConstants.RESOURCES_PATH + "BSG_Conquer_Maps";
        String filePath = baseFilePath + filenameDetails + ".json";
        String syncUrl = filter == null ? "https://anti.link/playlists/BSG_Conquer_Maps.json" : null;
        PlaylistGenerator.generatePlaylistFile(maps, title, clanIcon, filePath, "bsgconquer", syncUrl);
        if (filter != null) {
            String mapCount = Format.bold(String.valueOf(maps.size()));
            String message = "Found " + mapCount + " maps matching your filter:" + Format.bold(titleDetails.toString());
            Messages.sendPlainMessage(message, event.getChannel());
        }
        File file = new File(filePath);
        Messages.sendFile(file, title + ".json", (MessageChannel)event.getChannel());
    }
}

