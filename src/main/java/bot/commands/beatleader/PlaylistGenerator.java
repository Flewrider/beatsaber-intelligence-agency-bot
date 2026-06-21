package bot.commands.beatleader;

import bot.dto.clan.ClanMap;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class PlaylistGenerator {
    public static void generatePlaylistFile(List<ClanMap> maps, String title, String clanIcon, String filePath, String id, String syncUrl) {
        JSONObject playlist = new JSONObject();
        playlist.put("playlistTitle", (Object)title);
        playlist.put("playlistAuthor", (Object)"Beat Saber Germany");
        playlist.put("playlistDescription", (Object)"Maps to help the GER Clan rise!");
        playlist.put("image", (Object)clanIcon);
        JSONObject customData = new JSONObject();
        if (syncUrl != null) {
            customData.put("syncURL", (Object)syncUrl);
        }
        customData.put("owner", (Object)"Beat Saber Germany");
        customData.put("id", (Object)id);
        customData.put("hash", JSONObject.NULL);
        customData.put("shared", false);
        playlist.put("customData", (Object)customData);
        JSONArray songArray = new JSONArray();
        for (ClanMap map : maps) {
            JSONObject song = new JSONObject();
            song.put("songName", (Object)map.getSongName());
            song.put("levelAuthorName", (Object)map.getSongMapper());
            song.put("hash", (Object)map.getSongHash().toUpperCase());
            song.put("levelid", (Object)("custom_level_" + map.getSongHash().toUpperCase()));
            song.put("coverURL", (Object)map.getSongCover());
            JSONArray difficultiesArray = new JSONArray();
            JSONObject difficulty = new JSONObject();
            difficulty.put("characteristic", (Object)"Standard");
            difficulty.put("name", (Object)map.getDifficultyName().toLowerCase());
            difficultiesArray.put((Object)difficulty);
            song.put("difficulties", (Object)difficultiesArray);
            songArray.put((Object)song);
        }
        playlist.put("songs", (Object)songArray);
        try (FileWriter file = new FileWriter(filePath);){
            file.write(playlist.toString(4));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

