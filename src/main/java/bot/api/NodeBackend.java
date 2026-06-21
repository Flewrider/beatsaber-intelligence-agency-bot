package bot.api;

import bot.dto.backend.ComparisonPlayerData;
import bot.dto.backend.ComparisonRequest;
import bot.dto.clan.ClanMap;
import bot.dto.clan.ClanPlayer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class NodeBackend {
    final HttpMethods http;
    final Gson gson;

    public NodeBackend() {
        http = new HttpMethods();
        gson = new Gson();
    }

    public String generateComparisonImage(ComparisonPlayerData player1, ComparisonPlayerData player2) {

        String requestBody = gson.toJson(new ComparisonRequest(player1, player2));
        JsonObject response = http.fetchJsonObjectFromPost(ApiConstants.COMPARISON_URL, requestBody);
        if (response != null) {
            JsonElement imageElement = response.get("image");
            return imageElement.getAsString();
        }
        return null;
    }

    public List<ClanPlayer> getClanPlayers(String id) {
        JsonArray response = http.fetchJsonArray(ApiConstants.getClanPlayerUrl(id));
        if (response != null) {
            Type listType = new TypeToken<List<ClanPlayer>>() {}.getType();
            return gson.fromJson(response.toString(), listType);
        }
        return null;
    }

    public List<ClanMap> getClanMaps(String id, String sortBy, int maxPage) {
        JsonObject response = http.fetchJsonObject(ApiConstants.getClanMapsUrl(id, sortBy, maxPage));
        if (response != null) {
            Type listType = new TypeToken<List<ClanMap>>() {}.getType();
            return gson.fromJson(response.get("maps"), listType);
        }
        return null;
    }
}
