package bot.commands.beatleader;

import bot.db.DatabaseManager;
import bot.dto.MessageEventDTO;
import bot.dto.player.DataBasePlayer;
import bot.utils.Messages;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClanStats {
    private final DatabaseManager dbManager;
    private static final String BL_PROFILE_URL = "https://www.beatleader.xyz/u/";
    private static final String CLAN_URL = "https://beatleader.xyz/clan/GER";

    public ClanStats(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void executeClanStatsCommand(MessageEventDTO event) {
        Map<Long, Integer> captureCounts = this.dbManager.getCaptureCounts();
        List sortedEntries = captureCounts.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toList());
        StringBuilder leaderboard = new StringBuilder();
        for (int i = 0; i < Math.min(25, sortedEntries.size()); ++i) {
            Map.Entry entry = (Map.Entry)sortedEntries.get(i);
            String playerLine = this.formatPlayerRank((Long)entry.getKey(), i + 1, (Integer)entry.getValue());
            leaderboard.append(playerLine).append("\n");
        }
        Messages.sendMessageWithTitle(leaderboard.toString(), "\ud83d\udc51 Clan Capture Leaderboard \ud83d\udc51", CLAN_URL, event);
    }

    private String formatPlayerRank(Long playerId, int rank, int captureCount) {
        DataBasePlayer player = this.dbManager.getPlayerById(playerId);
        String playerName = player != null ? player.getName() : "Unregistered Player";
        String playerProfileLink = BL_PROFILE_URL + playerId;
        String medalEmoji = this.getMedalEmoji(rank);
        return medalEmoji + " **" + String.format("[%s](%s)** - Captures: **%d**", playerName, playerProfileLink, captureCount);
    }

    private String getMedalEmoji(int rank) {
        switch (rank) {
            case 1: {
                return ":first_place_medal: ";
            }
            case 2: {
                return ":second_place_medal: ";
            }
            case 3: {
                return ":third_place_medal: ";
            }
        }
        return "#" + rank + " ";
    }
}

