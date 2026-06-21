package bot.dto.clan;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LeaderboardInfo {
    @SerializedName(value="id")
    private String leaderboardId;
    @SerializedName(value="song")
    private SongInfo song;
    @SerializedName(value="clan")
    private ClanInfo clan;
    @SerializedName(value="scores")
    private List<Score> scores;

    public String getLeaderboardId() {
        return this.leaderboardId;
    }

    public void setLeaderboardId(String leaderboardId) {
        this.leaderboardId = leaderboardId;
    }

    public SongInfo getSong() {
        return this.song;
    }

    public void setSong(SongInfo song) {
        this.song = song;
    }

    public ClanInfo getClan() {
        return this.clan;
    }

    public void setClan(ClanInfo clan) {
        this.clan = clan;
    }

    public List<Score> getScores() {
        return this.scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public static class Score {
        @SerializedName(value="timeset")
        private String timeset;
        @SerializedName(value="player")
        private PlayerInfo player;

        public String getTimeset() {
            return this.timeset;
        }

        public void setTimeset(String timeset) {
            this.timeset = timeset;
        }

        public PlayerInfo getPlayer() {
            return this.player;
        }

        public void setPlayer(PlayerInfo player) {
            this.player = player;
        }

        public static class PlayerInfo {
            @SerializedName(value="id")
            private String playerId;
            @SerializedName(value="name")
            private String name;
            @SerializedName(value="clans")
            private List<ClanInfo> clans;

            public String getPlayerId() {
                return this.playerId;
            }

            public void setPlayerId(String playerId) {
                this.playerId = playerId;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ClanInfo> getClans() {
                return this.clans;
            }

            public void setClans(List<ClanInfo> clans) {
                this.clans = clans;
            }
        }
    }

    public static class ClanInfo {
        @SerializedName(value="id")
        private int clanId;
        @SerializedName(value="name")
        private String name;
        @SerializedName(value="tag")
        private String tag;
        @SerializedName(value="icon")
        private String icon;
        @SerializedName(value="rank")
        private int rank;

        public int getClanId() {
            return this.clanId;
        }

        public void setClanId(int clanId) {
            this.clanId = clanId;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getRank() {
            return this.rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }

    public static class SongInfo {
        @SerializedName(value="id")
        private String songId;
        @SerializedName(value="name")
        private String name;
        @SerializedName(value="author")
        private String author;
        @SerializedName(value="mapper")
        private String mapper;
        @SerializedName(value="coverImage")
        private String coverImage;
        @SerializedName(value="bpm")
        private float bpm;
        @SerializedName(value="duration")
        private int duration;

        public String getSongId() {
            return this.songId;
        }

        public void setSongId(String songId) {
            this.songId = songId;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return this.author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getMapper() {
            return this.mapper;
        }

        public void setMapper(String mapper) {
            this.mapper = mapper;
        }

        public String getCoverImage() {
            return this.coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public float getBpm() {
            return this.bpm;
        }

        public void setBpm(int bpm) {
            this.bpm = bpm;
        }

        public int getDuration() {
            return this.duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }
}

