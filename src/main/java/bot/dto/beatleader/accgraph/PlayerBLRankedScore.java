package bot.dto.beatleader.accgraph;

import com.google.gson.annotations.SerializedName;

public class PlayerBLRankedScore {
    @SerializedName(value="acc")
    private double acc;
    @SerializedName(value="songName")
    private String songName;
    @SerializedName(value="diff")
    private String diff;
    @SerializedName(value="mapper")
    private String mapper;
    @SerializedName(value="leaderboardId")
    private String leaderboardId;
    @SerializedName(value="stars")
    private double stars;
    @SerializedName(value="modifiers")
    private String modifiers;
    @SerializedName(value="techRating")
    private double techRating;
    @SerializedName(value="mode")
    private String mode;
    @SerializedName(value="passRating")
    private double passRating;
    @SerializedName(value="timeset")
    private int timeset;
    @SerializedName(value="accRating")
    private double accRating;
    @SerializedName(value="hash")
    private String hash;

    public double getAcc() {
        return this.acc;
    }

    public String getSongName() {
        return this.songName;
    }

    public String getDiff() {
        return this.diff;
    }

    public String getMapper() {
        return this.mapper;
    }

    public String getLeaderboardId() {
        return this.leaderboardId;
    }

    public double getStars() {
        return this.stars;
    }

    public String getModifiers() {
        return this.modifiers;
    }

    public Object getTechRating() {
        return this.techRating;
    }

    public String getMode() {
        return this.mode;
    }

    public double getPassRating() {
        return this.passRating;
    }

    public int getTimeset() {
        return this.timeset;
    }

    public double getAccRating() {
        return this.accRating;
    }

    public String getHash() {
        return this.hash;
    }
}

