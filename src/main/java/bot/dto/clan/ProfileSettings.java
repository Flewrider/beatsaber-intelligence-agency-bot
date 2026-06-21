package bot.dto.clan;

import com.google.gson.annotations.SerializedName;

public class ProfileSettings {
    @SerializedName(value="profileCover")
    private Object profileCover;
    @SerializedName(value="bio")
    private Object bio;
    @SerializedName(value="message")
    private Object message;
    @SerializedName(value="showBots")
    private boolean showBots;
    @SerializedName(value="saturation")
    private float saturation;
    @SerializedName(value="starredFriends")
    private String starredFriends;
    @SerializedName(value="profileAppearance")
    private String profileAppearance;
    @SerializedName(value="leftSaberColor")
    private Object leftSaberColor;
    @SerializedName(value="hue")
    private int hue;
    @SerializedName(value="id")
    private int id;
    @SerializedName(value="showAllRatings")
    private boolean showAllRatings;
    @SerializedName(value="rightSaberColor")
    private Object rightSaberColor;
    @SerializedName(value="effectName")
    private String effectName;

    public void setProfileCover(Object profileCover) {
        this.profileCover = profileCover;
    }

    public Object getProfileCover() {
        return this.profileCover;
    }

    public void setBio(Object bio) {
        this.bio = bio;
    }

    public Object getBio() {
        return this.bio;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getMessage() {
        return this.message;
    }

    public void setShowBots(boolean showBots) {
        this.showBots = showBots;
    }

    public boolean isShowBots() {
        return this.showBots;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public float getSaturation() {
        return this.saturation;
    }

    public void setStarredFriends(String starredFriends) {
        this.starredFriends = starredFriends;
    }

    public String getStarredFriends() {
        return this.starredFriends;
    }

    public void setProfileAppearance(String profileAppearance) {
        this.profileAppearance = profileAppearance;
    }

    public String getProfileAppearance() {
        return this.profileAppearance;
    }

    public void setLeftSaberColor(Object leftSaberColor) {
        this.leftSaberColor = leftSaberColor;
    }

    public Object getLeftSaberColor() {
        return this.leftSaberColor;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getHue() {
        return this.hue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setShowAllRatings(boolean showAllRatings) {
        this.showAllRatings = showAllRatings;
    }

    public boolean isShowAllRatings() {
        return this.showAllRatings;
    }

    public void setRightSaberColor(Object rightSaberColor) {
        this.rightSaberColor = rightSaberColor;
    }

    public Object getRightSaberColor() {
        return this.rightSaberColor;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public String getEffectName() {
        return this.effectName;
    }
}

