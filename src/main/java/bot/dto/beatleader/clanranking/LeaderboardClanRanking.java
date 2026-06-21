package bot.dto.beatleader.clanranking;

import bot.dto.beatleader.clanranking.ClanRankingItem;
import java.util.List;

public class LeaderboardClanRanking {
    private List<ClanRankingItem> clanRanking;

    public List<ClanRankingItem> getClanRanking() {
        return this.clanRanking;
    }

    public void setClanRanking(List<ClanRankingItem> clanRanking) {
        this.clanRanking = clanRanking;
    }
}

