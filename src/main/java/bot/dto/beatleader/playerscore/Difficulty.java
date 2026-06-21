package bot.dto.beatleader.playerscore;

import com.google.gson.annotations.SerializedName;

public class Difficulty {
    @SerializedName(value="modeName")
    private String modeName;
    @SerializedName(value="id")
    private int id;
    @SerializedName(value="stars")
    private float stars;
    @SerializedName(value="value")
    private int value;
    @SerializedName(value="difficultyName")
    private String difficultyName;

    public String getModeName() {
        return this.modeName;
    }

    public int getId() {
        return this.id;
    }

    public float getStars() {
        return this.stars;
    }

    public int getValue() {
        return this.value;
    }

    public String getDifficultyName() {
        return this.difficultyName;
    }
}

