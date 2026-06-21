package bot.commands.beatleader;

import bot.api.ApiConstants;
import bot.api.BeatLeader;
import bot.api.NodeBackend;
import bot.commands.beatleader.PlaylistGenerator;
import bot.dto.MessageEventDTO;
import bot.dto.beatleader.clanranking.ClanRankingItem;
import bot.dto.clan.ClanMap;
import bot.main.BotConstants;
import bot.utils.Format;
import bot.utils.Messages;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import net.dv8tion.jda.api.entities.MessageChannel;

public class ClanRaid {
    NodeBackend backend = new NodeBackend();
    BeatLeader bl = new BeatLeader();

    public void sendClanRaidPlaylist(String clanTag, MessageEventDTO event) {
        if (clanTag != null && clanTag.equalsIgnoreCase("GER")) {
            Messages.sendMessage("Haha no.", event);
            return;
        }
        Messages.sendTempMessage("Loading maps. Please wait... \u2728", 14, (MessageChannel)event.getChannel());
        List<ClanMap> maps = this.backend.getClanMaps(clanTag, "tohold", 10);
        if (maps.isEmpty()) {
            Messages.sendMessage("No maps found to conquer.", event);
            return;
        }
        HashMap<String, Double> mapPpDifferences = new HashMap<String, Double>();
        ArrayList<ClanMap> mapsToRaid = new ArrayList<ClanMap>();
        String targetClanIcon = null;
        for (ClanMap mapToRaid : maps) {
            ClanRankingItem targetClan;
            String leaderboardId = mapToRaid.getLeaderboardId();
            List<ClanRankingItem> clanRanking = this.bl.getLeaderboardClanRanking(leaderboardId);
            ClanRankingItem gerClan = clanRanking.stream().filter(item -> item.getClan().getTag().equalsIgnoreCase("GER")).findFirst().orElse(null);
            if (gerClan == null || (targetClan = (ClanRankingItem)clanRanking.stream().filter(item -> item.getClan().getTag().equalsIgnoreCase(clanTag)).findFirst().orElse(null)) == null) continue;
            if (targetClanIcon == null) {
                targetClanIcon = targetClan.getClan().getIcon();
            }
            double ppDifference = Math.abs(gerClan.getPp() - targetClan.getPp());
            mapPpDifferences.put(mapToRaid.getSongName(), ppDifference);
            mapsToRaid.add(mapToRaid);
        }
        mapsToRaid.sort(Comparator.comparingDouble(m -> (Double)mapPpDifferences.get(m.getSongName())));
        List ppDifferencesList = mapPpDifferences.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(entry -> String.format("%-40s: %.3f PP", entry.getKey(), entry.getValue())).collect(Collectors.toList());
        Object ppDifferencesMessage = String.join((CharSequence)"\n", ppDifferencesList.subList(0, Math.min(25, ppDifferencesList.size())));
        if (ppDifferencesList.size() > 25) {
            ppDifferencesMessage = (String)ppDifferencesMessage + "\n...and " + (ppDifferencesList.size() - 25) + " more";
        }
        ppDifferencesMessage = Format.codeAutohotkey((String)ppDifferencesMessage);
        String titleUrl = ApiConstants.getBeatLeaderClanToHoldMapsURL(clanTag);
        Messages.sendMessageWithTitle((String)ppDifferencesMessage, Format.underline("PP Differences between " + Format.bold("GER") + " and " + Format.bold(clanTag)), titleUrl, event);
        String title = clanTag + " Clan Raid Maps";
        String clanRaidIcon = this.getBase64ImageFromUrl(targetClanIcon);
        if (clanRaidIcon == null) {
            clanRaidIcon = this.getBase64ImageFromUrl("https://i.imgur.com/2V038wD.png");
        }
        String fileName = "BSG_Raid_Maps_" + clanTag + ".json";
        String filePath = BotConstants.RESOURCES_PATH + fileName;
        PlaylistGenerator.generatePlaylistFile(mapsToRaid, title, clanRaidIcon, filePath, "bsgraid" + clanTag + System.currentTimeMillis(), null);
        File file = new File(filePath);
        Messages.sendFile(file, fileName, (MessageChannel)event.getChannel());
    }

    public String getBase64ImageFromUrl(String urlString) {
        if (urlString == null) {
            return null;
        }
        try {
            URL url = new URL(urlString);
            BufferedImage image = ImageIO.read(url);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage)image, "png", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            return "base64," + Base64.getEncoder().encodeToString(imageBytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

