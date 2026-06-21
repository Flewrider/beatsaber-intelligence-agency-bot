package bot.dto.beatleader.playerscore;

import bot.dto.beatleader.playerscore.Difficulty;
import bot.dto.beatleader.playerscore.Performance;
import bot.dto.beatleader.playerscore.Player;
import bot.dto.beatleader.playerscore.Song;
import com.google.gson.annotations.SerializedName;

public class BLPlayerScore {
    @SerializedName(value="difficulty")
    private Difficulty difficulty;
    @SerializedName(value="song")
    private Song song;
    @SerializedName(value="performance")
    private Performance performance;
    @SerializedName(value="replay")
    private String replay;
    @SerializedName(value="player")
    private Player player;

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public Song getSong() {
        return this.song;
    }

    public Performance getPerformance() {
        return this.performance;
    }

    public String getReplay() {
        return this.replay;
    }

    public Player getPlayer() {
        return this.player;
    }
}

