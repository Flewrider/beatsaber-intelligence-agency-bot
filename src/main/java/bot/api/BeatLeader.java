package bot.api;

import bot.dto.PlayerScore;
import bot.dto.beatleader.accgraph.PlayerBLRankedScore;
import bot.dto.beatleader.clanranking.ClanRankingItem;
import bot.dto.beatleader.clanranking.LeaderboardClanRanking;
import bot.dto.beatleader.history.PlayerHistoryItem;
import bot.dto.beatleader.player.BeatLeaderPlayer;
import bot.dto.beatleader.playerscore.BLPlayerScore;
import bot.dto.beatleader.scores.PlayerScoreBL;
import bot.dto.clan.LeaderboardInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BeatLeader {

    final HttpMethods http;
    final Gson gson;

    public BeatLeader() {
        http = new HttpMethods();
        gson = new Gson();
    }

    public LeaderboardInfo getLeaderboardInfo(String leaderboardId) {
        String url = ApiConstants.getBeatLeaderLeaderboardURL(leaderboardId);
        JsonObject response = http.fetchJsonObject(url);
        if (response != null) {
            return gson.fromJson(response.toString(), LeaderboardInfo.class);
        }
        return new LeaderboardInfo();
    }

    public List<ClanRankingItem> getLeaderboardClanRanking(String leaderboardId) {
        String url = ApiConstants.getBeatLeaderLeaderboardClanRankingURL(leaderboardId);
        JsonObject response = http.fetchJsonObject(url);
        if (response != null) {
            LeaderboardClanRanking ranking = gson.fromJson(response.toString(), LeaderboardClanRanking.class);
            if (ranking != null) {
                return ranking.getClanRanking();
            }
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public BLPlayerScore getPlayerScore(String leaderboardContext, long playerId, String hash, String diffName, String characteristic) {
        String url = ApiConstants.getBeatLeaderPlayerScoreURL(leaderboardContext, String.valueOf(playerId), hash, diffName, characteristic);
        JsonObject response = http.fetchJsonObject(url);
        if (response != null) {
            return gson.fromJson(response.toString(), BLPlayerScore.class);
        }
        return null;
    }

    public List<PlayerBLRankedScore> getAllPlayerRankedScores(long playerId) {
        String url = ApiConstants.getBeatLeaderRankedScoresURL(String.valueOf(playerId));
        JsonArray response = http.fetchJsonArray(url);
        if (response != null) {
            Type listType = new TypeToken<List<PlayerBLRankedScore>>() {}.getType();
            return gson.fromJson(response.toString(), listType);
        }
        return null;
    }

    public List<PlayerScore> getTopScoresByPlayerIdAndPage(long playerId, int pageNr) {
        String url = ApiConstants.getBeatLeaderTopScoresURL(String.valueOf(playerId), pageNr);
        return getPlayerScores(url);
    }

    public List<PlayerScore> getRecentScoresByPlayerIdAndPage(long playerId, int pageNr) {
        String url = ApiConstants.getBeatLeaderRecentScoresURL(String.valueOf(playerId), pageNr);
        return getPlayerScores(url);
    }

    @Nullable
    private List<PlayerScore> getPlayerScores(String recentScoresUrl) {
        JsonObject response = http.fetchJsonObject(recentScoresUrl);
        if (response != null) {
            JsonArray topScores = response.getAsJsonArray("data");
            Type listType = new TypeToken<List<PlayerScoreBL>>() {}.getType();
            return gson.fromJson(topScores.toString(), listType);
        }
        return new ArrayList<>();
    }

    public List<PlayerHistoryItem> getPlayerHistoryById(long playerId) {
        String url = ApiConstants.getBeatLeaderPlayerHistoryURL(String.valueOf(playerId));
        JsonArray response = http.fetchJsonArray(url);
        if (response != null) {
            Type listType = new TypeToken<List<PlayerHistoryItem>>() {}.getType();
            return gson.fromJson(response.toString(), listType);
        }
        return new ArrayList<>();
    }

    public BeatLeaderPlayer getPlayerById(long playerId) {
        String url = ApiConstants.getBeatLeaderPlayerByIdURL(playerId);
        JsonObject response = http.fetchJsonObject(url);
        if (response != null) {
            return gson.fromJson(response.toString(), BeatLeaderPlayer.class);
        }
        return null;
    }

    public BeatLeaderPlayer getPlayerByDiscordID(long discordId) {
        //TODO BROKEN?
        String url = ApiConstants.getBeatLeaderPlayerByDiscordId(discordId);
        JsonObject response = http.fetchJsonObject(url);
        if (response != null) {
            return gson.fromJson(response.toString(), BeatLeaderPlayer.class);
        }
        return null;
    }
}
