package bot.main.beatleader;

import bot.api.NodeBackend;
import bot.commands.beatleader.PlaylistGenerator;
import bot.dto.clan.ClanMap;
import bot.main.BotConstants;
import bot.utils.DiscordLogger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledPlaylistGenerator {
    private final NodeBackend backend = new NodeBackend();

    public void start() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::generatePlaylist, 3L, 15L, TimeUnit.MINUTES);
    }

    private void generatePlaylist() {
        if (System.getenv("clan_playlist_path") == null) {
            DiscordLogger.sendLogInChannel("CLAN_PLAYLIST_PATH NOT SET", "errors");
            return;
        }
        try {
            String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM. HH:mm"));
            String playlistTitle = "GER Conquer Maps - " + formattedDateTime;
            String filePath = System.getenv("clan_playlist_path") + "BSG_Conquer_Maps.json";
            List<ClanMap> maps = this.backend.getClanMaps("GER", "toconquer", 10);
            if (maps == null) {
                DiscordLogger.sendLogInChannel("Could not fetch clan maps; skipping playlist generation this cycle.", "errors");
                return;
            }
            PlaylistGenerator.generatePlaylistFile(maps, playlistTitle, BotConstants.playlistImageBsg, filePath, "bsgconquer", "https://anti.link/playlists/BSG_Conquer_Maps.json");
            DiscordLogger.sendLogInChannel("\ud83d\udd35 Updated clan sync playlist", "foaa-refresh");
        }
        catch (Exception e) {
            e.printStackTrace();
            DiscordLogger.sendLogInChannel(e.toString(), "errors");
        }
    }
}

