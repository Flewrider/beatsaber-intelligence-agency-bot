package bot.dto.beatleader.playerscore;

import com.google.gson.annotations.SerializedName;

public class Song {
    @SerializedName(value="cover")
    private String cover;
    @SerializedName(value="author")
    private String author;
    @SerializedName(value="name")
    private String name;
    @SerializedName(value="mapper")
    private String mapper;
    @SerializedName(value="id")
    private String id;

    public String getCover() {
        return this.cover;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getName() {
        return this.name;
    }

    public String getMapper() {
        return this.mapper;
    }

    public String getId() {
        return this.id;
    }
}

