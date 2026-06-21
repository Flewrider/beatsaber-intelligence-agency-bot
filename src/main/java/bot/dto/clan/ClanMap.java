package bot.dto.clan;

import com.google.gson.annotations.SerializedName;

public class ClanMap {
    @SerializedName(value="rank")
    private int rank;
    @SerializedName(value="ppDiff")
    private double ppDiff;
    @SerializedName(value="lastUpdateTime")
    private long lastUpdateTime;
    @SerializedName(value="averageAccuracy")
    private double averageAccuracy;
    @SerializedName(value="leaderboardId")
    private String leaderboardId;
    @SerializedName(value="songId")
    private String songId;
    @SerializedName(value="songHash")
    private String songHash;
    @SerializedName(value="songName")
    private String songName;
    @SerializedName(value="songMapper")
    private String songMapper;
    @SerializedName(value="songCover")
    private String songCover;
    @SerializedName(value="songBpm")
    private float songBpm;
    @SerializedName(value="songDuration")
    private int songDuration;
    @SerializedName(value="difficultyName")
    private String difficultyName;
    @SerializedName(value="plays")
    private int plays;
    @SerializedName(value="playCount")
    private int playCount;
    @SerializedName(value="positiveVotes")
    private int positiveVotes;
    @SerializedName(value="negativeVotes")
    private int negativeVotes;
    @SerializedName(value="accRating")
    private float accRating;
    @SerializedName(value="passRating")
    private float passRating;
    @SerializedName(value="techRating")
    private float techRating;

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getPpDiff() {
        return this.ppDiff;
    }

    public void setPpDiff(double ppDiff) {
        this.ppDiff = ppDiff;
    }

    public long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public double getAverageAccuracy() {
        return this.averageAccuracy;
    }

    public void setAverageAccuracy(double averageAccuracy) {
        this.averageAccuracy = averageAccuracy;
    }

    public String getLeaderboardId() {
        return this.leaderboardId;
    }

    public void setLeaderboardId(String leaderboardId) {
        this.leaderboardId = leaderboardId;
    }

    public String getSongId() {
        return this.songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongMapper() {
        return this.songMapper;
    }

    public void setSongMapper(String songMapper) {
        this.songMapper = songMapper;
    }

    public String getSongCover() {
        return this.songCover;
    }

    public void setSongCover(String songCover) {
        this.songCover = songCover;
    }

    public float getSongBpm() {
        return this.songBpm;
    }

    public void setSongBpm(int songBpm) {
        this.songBpm = songBpm;
    }

    public int getSongDuration() {
        return this.songDuration;
    }

    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }

    public String getDifficultyName() {
        return this.difficultyName;
    }

    public void setDifficultyName(String difficultyName) {
        this.difficultyName = difficultyName;
    }

    public int getPlays() {
        return this.plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public int getPlayCount() {
        return this.playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getPositiveVotes() {
        return this.positiveVotes;
    }

    public void setPositiveVotes(int positiveVotes) {
        this.positiveVotes = positiveVotes;
    }

    public int getNegativeVotes() {
        return this.negativeVotes;
    }

    public void setNegativeVotes(int negativeVotes) {
        this.negativeVotes = negativeVotes;
    }

    public String getSongHash() {
        return this.songHash;
    }

    public void setSongHash(String songHash) {
        this.songHash = songHash;
    }

    public void setSongBpm(float songBpm) {
        this.songBpm = songBpm;
    }

    public float getAccRating() {
        return this.accRating;
    }

    public void setAccRating(float accRating) {
        this.accRating = accRating;
    }

    public float getPassRating() {
        return this.passRating;
    }

    public void setPassRating(float passRating) {
        this.passRating = passRating;
    }

    public float getTechRating() {
        return this.techRating;
    }

    public void setTechRating(float techRating) {
        this.techRating = techRating;
    }
}

