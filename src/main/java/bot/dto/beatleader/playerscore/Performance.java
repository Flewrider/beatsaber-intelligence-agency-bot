package bot.dto.beatleader.playerscore;

import com.google.gson.annotations.SerializedName;

public class Performance {
    @SerializedName(value="pp")
    private float pp;
    @SerializedName(value="fullCombo")
    private boolean fullCombo;
    @SerializedName(value="pauses")
    private int pauses;
    @SerializedName(value="modifiedScore")
    private int modifiedScore;
    @SerializedName(value="accuracy")
    private Object accuracy;
    @SerializedName(value="rank")
    private int rank;
    @SerializedName(value="baseScore")
    private int baseScore;
    @SerializedName(value="missedNotes")
    private int missedNotes;
    @SerializedName(value="maxCombo")
    private int maxCombo;
    @SerializedName(value="badCuts")
    private int badCuts;

    public float getPp() {
        return this.pp;
    }

    public boolean isFullCombo() {
        return this.fullCombo;
    }

    public int getPauses() {
        return this.pauses;
    }

    public int getModifiedScore() {
        return this.modifiedScore;
    }

    public Object getAccuracy() {
        return this.accuracy;
    }

    public int getRank() {
        return this.rank;
    }

    public int getBaseScore() {
        return this.baseScore;
    }

    public int getMissedNotes() {
        return this.missedNotes;
    }

    public int getMaxCombo() {
        return this.maxCombo;
    }

    public int getBadCuts() {
        return this.badCuts;
    }
}

