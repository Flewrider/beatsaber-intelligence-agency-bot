package bot.dto.beatleader;

public class ClanPlaylistFilter {
    public float maxAccRating;
    public float maxPassRating;
    public float maxTechRating;
    public boolean unplayedOnly;

    public ClanPlaylistFilter(float maxAccRating, float maxPassRating, float maxTechRating, boolean unplayedOnly) {
        this.maxAccRating = maxAccRating;
        this.maxPassRating = maxPassRating;
        this.maxTechRating = maxTechRating;
        this.unplayedOnly = unplayedOnly;
    }

    public float getMaxAccRating() {
        return this.maxAccRating;
    }

    public void setMaxAccRating(float maxAccRating) {
        this.maxAccRating = maxAccRating;
    }

    public float getMaxPassRating() {
        return this.maxPassRating;
    }

    public void setMaxPassRating(float maxPassRating) {
        this.maxPassRating = maxPassRating;
    }

    public float getMaxTechRating() {
        return this.maxTechRating;
    }

    public void setMaxTechRating(float maxTechRating) {
        this.maxTechRating = maxTechRating;
    }

    public boolean isUnplayedOnly() {
        return this.unplayedOnly;
    }

    public void setUnplayedOnly(boolean unplayedOnly) {
        this.unplayedOnly = unplayedOnly;
    }
}

