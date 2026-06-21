package bot.dto.beatleader.playerscore;

import com.google.gson.annotations.SerializedName;

public class Player {
    @SerializedName(value="country")
    private String country;
    @SerializedName(value="name")
    private String name;
    @SerializedName(value="rank")
    private int rank;
    @SerializedName(value="id")
    private String id;

    public String getCountry() {
        return this.country;
    }

    public String getName() {
        return this.name;
    }

    public int getRank() {
        return this.rank;
    }

    public String getId() {
        return this.id;
    }
}

