package bot.dto.beatleader.clanranking;

public class ClanRankingItem {
    private int id;
    private Clan clan;
    private int rank;
    private double pp;
    private double averageRank;
    private double averageAccuracy;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clan getClan() {
        return this.clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getPp() {
        return this.pp;
    }

    public void setPp(double pp) {
        this.pp = pp;
    }

    public double getAverageRank() {
        return this.averageRank;
    }

    public void setAverageRank(double averageRank) {
        this.averageRank = averageRank;
    }

    public double getAverageAccuracy() {
        return this.averageAccuracy;
    }

    public void setAverageAccuracy(double averageAccuracy) {
        this.averageAccuracy = averageAccuracy;
    }

    public static class Clan {
        private int id;
        private String name;
        private String color;
        private String icon;
        private String tag;
        private String leaderID;
        private String description;
        private String bio;
        private int playersCount;

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColor() {
            return this.color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getLeaderID() {
            return this.leaderID;
        }

        public void setLeaderID(String leaderID) {
            this.leaderID = leaderID;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBio() {
            return this.bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public int getPlayersCount() {
            return this.playersCount;
        }

        public void setPlayersCount(int playersCount) {
            this.playersCount = playersCount;
        }
    }
}

