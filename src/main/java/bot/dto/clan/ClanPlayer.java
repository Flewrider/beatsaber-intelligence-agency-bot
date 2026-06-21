package bot.dto.clan;

import bot.dto.clan.ProfileSettings;
import com.google.gson.annotations.SerializedName;

public class ClanPlayer {
    @SerializedName(value="pp")
    private double pp;
    @SerializedName(value="country")
    private String country;
    @SerializedName(value="role")
    private String role;
    @SerializedName(value="contextExtensions")
    private Object contextExtensions;
    @SerializedName(value="bot")
    private boolean bot;
    @SerializedName(value="avatar")
    private String avatar;
    @SerializedName(value="clanOrder")
    private String clanOrder;
    @SerializedName(value="platform")
    private String platform;
    @SerializedName(value="profileSettings")
    private ProfileSettings profileSettings;
    @SerializedName(value="patreonFeatures")
    private Object patreonFeatures;
    @SerializedName(value="name")
    private String name;
    @SerializedName(value="clans")
    private Object clans;
    @SerializedName(value="rank")
    private int rank;
    @SerializedName(value="id")
    private String id;
    @SerializedName(value="socials")
    private Object socials;
    @SerializedName(value="countryRank")
    private int countryRank;

    public void setPp(double pp) {
        this.pp = pp;
    }

    public double getPp() {
        return this.pp;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return this.country;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public void setContextExtensions(Object contextExtensions) {
        this.contextExtensions = contextExtensions;
    }

    public Object getContextExtensions() {
        return this.contextExtensions;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public boolean isBot() {
        return this.bot;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setClanOrder(String clanOrder) {
        this.clanOrder = clanOrder;
    }

    public String getClanOrder() {
        return this.clanOrder;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setProfileSettings(ProfileSettings profileSettings) {
        this.profileSettings = profileSettings;
    }

    public ProfileSettings getProfileSettings() {
        return this.profileSettings;
    }

    public void setPatreonFeatures(Object patreonFeatures) {
        this.patreonFeatures = patreonFeatures;
    }

    public Object getPatreonFeatures() {
        return this.patreonFeatures;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setClans(Object clans) {
        this.clans = clans;
    }

    public Object getClans() {
        return this.clans;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setSocials(Object socials) {
        this.socials = socials;
    }

    public Object getSocials() {
        return this.socials;
    }

    public void setCountryRank(int countryRank) {
        this.countryRank = countryRank;
    }

    public int getCountryRank() {
        return this.countryRank;
    }
}

