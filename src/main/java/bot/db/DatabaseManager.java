package bot.db;

import bot.dto.player.DataBasePlayer;
import bot.dto.player.PlayerSkills;
import bot.dto.supporters.SupporterInfo;
import bot.utils.DiscordLogger;
import bot.utils.Supporters;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import net.dv8tion.jda.api.entities.User;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> playersCollection;
    private MongoCollection<Document> supportersCollection;
    private MongoCollection<Document> captureCountsCollection;

    public void connectToDatabase() {
        try {
            if (mongoClient == null) {
                String connectionUrl = "mongodb://" + DBConstants.DB_USERNAME + ":" + DBConstants.DB_PASSWORD + "@" + DBConstants.DB_HOST + ":" + DBConstants.DB_PORT + "/?authSource=admin";
                System.out.println(connectionUrl);
                mongoClient = new MongoClient(new MongoClientURI(connectionUrl));
                database = mongoClient.getDatabase(DBConstants.DB_DATABASE);
                playersCollection = database.getCollection("players");
                supportersCollection = database.getCollection("supporters");
                captureCountsCollection = database.getCollection("clancapturecounts");
                System.out.println("*** Connected to database: MongoDB");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DiscordLogger.sendLogInChannel(ExceptionUtils.getStackTrace(e), DiscordLogger.DB);
        }
    }

    public boolean savePlayer(DataBasePlayer player) {
        if (playersCollection == null) {
            return false;
        }
        DataBasePlayer storedPlayer = getPlayerByDiscordId(player.getDiscordUserId());
        if (storedPlayer != null) {
            return false;
        }
        try {
            Document playerDocument = player.toDocument();
            playersCollection.insertOne(playerDocument);
            return true;
        } catch (Exception e) {
            DiscordLogger.sendLogInChannel(ExceptionUtils.getStackTrace(e), DiscordLogger.DB);
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePlayerByDiscordUserId(long userId) {
        if (database == null) {
            return false;
        }
        MongoCollection<Document> playersCollection = database.getCollection("players");
        DeleteResult result = playersCollection.deleteOne(Filters.eq("player_discord_user_id", userId));
        return result.getDeletedCount() > 0;
    }

    public void updatePlayer(DataBasePlayer newPlayer) {
        if (database == null) {
            return;
        }
        MongoCollection<Document> playersCollection = database.getCollection("players");
        Document playerData = newPlayer.toDocument();
        Bson filter = Filters.or(
                Filters.eq("player_id", newPlayer.getPlayerIdLong()),
                Filters.eq("player_id", newPlayer.getId())
        );
        playersCollection.updateOne(filter, new Document("$set", playerData));
    }

    public List<DataBasePlayer> getAllStoredPlayers() {
        if (database == null) {
            return null;
        }
        List<DataBasePlayer> players = new ArrayList<>();
        MongoCollection<Document> playersCollection = database.getCollection("players");
        FindIterable<Document> documents = playersCollection.find();
        for (Document document : documents) {
            DataBasePlayer player = DataBasePlayer.fromDocument(document);
            players.add(player);
        }
        return players;
    }

    public DataBasePlayer getPlayerByName(String playerName) {
        if (database == null) {
            return null;
        }
        Document document = database.getCollection("players").find(Filters.eq("player_name", playerName)).first();
        return document != null ? DataBasePlayer.fromDocument(document) : null;
    }

    public DataBasePlayer getPlayerById(long playerId) {
        Document document = playersCollection.find(Filters.eq("player_id", playerId)).first();
        return document != null ? DataBasePlayer.fromDocument(document) : null;
    }

    public void incrementCaptureCount(long playerId) {
        Document document = captureCountsCollection.find(Filters.eq("player_id", playerId)).first();
        if (document != null) {
            int count = document.getInteger("count");
            captureCountsCollection.updateOne(Filters.eq("player_id", playerId), new Document("$set", new Document("count", count + 1)));
        } else {
            document = new Document("player_id", playerId).append("count", 1);
            captureCountsCollection.insertOne(document);
        }
    }

    public Map<Long, Integer> getCaptureCounts() {
        Map<Long, Integer> counts = new HashMap<>();
        FindIterable<Document> documents = captureCountsCollection.find();
        for (Document doc : documents) {
            Number playerId = doc.get("player_id", Number.class);
            Integer count = doc.getInteger("count", 0);
            counts.put(playerId.longValue(), count);
        }
        return counts;
    }

    public int getCaptureCount(long playerId) {
        Document document = captureCountsCollection.find(Filters.eq("player_id", playerId)).first();
        return document != null ? document.getInteger("count", 0) : 0;
    }

    public DataBasePlayer getPlayerByDiscordId(long discordUserId) {
        if (database == null) {
            return null;
        }
        Document document = database.getCollection("players").find(Filters.eq("player_discord_user_id", discordUserId)).first();
        return document != null ? DataBasePlayer.fromDocument(document) : null;
    }

    public PlayerSkills getPlayerSkillsByDiscordId(long discordId) {
        if (database == null) {
            return null;
        }
        Document document = database.getCollection("skills").find(Filters.eq("discord_user_id", discordId)).first();
        return document != null ? PlayerSkills.fromDocument(document) : null;
    }

    public int setSkill(long idLong, String skill, int newValue) {
        if (database == null) {
            return -1;
        }
        Document document = database.getCollection("skills").find(Filters.eq("discord_user_id", idLong)).first();
        String updateField = "skills." + skill;
        if (document != null) {
            database.getCollection("skills").updateOne(Filters.eq("discord_id", idLong), new Document("$set", new Document(updateField, newValue)));
        } else {
            Document newSkill = new Document("discord_id", idLong)
                    .append("player_name", getPlayerByDiscordId(idLong).getName())
                    .append("skills", new Document(skill, newValue));
            database.getCollection("skills").insertOne(newSkill);
        }
        return 1;
    }

    public SupporterInfo updateAndRetrieveSupporterInfoByDiscordId(User user) {
        if (database == null) {
            return null;
        }
        Document document = supportersCollection.find(Filters.eq("discord_user_id", user.getId())).first();

        List<String> supportTypes = Supporters.getUserSupportTypes(user);
        SupporterInfo supporterInfo;

        if (document != null) {
            supporterInfo = SupporterInfo.fromDocument(document);
            supporterInfo.setSupportTypes(supportTypes);

            Document updatedDocument = supporterInfo.toDocument();
            supportersCollection.updateOne(Filters.eq("discord_user_id", user.getId()), new Document("$set", updatedDocument));
        } else {
            supporterInfo = new SupporterInfo(user.getId());
            supporterInfo.setSupportTypes(supportTypes);

            Document newDocument = supporterInfo.toDocument();
            supportersCollection.insertOne(newDocument);
        }

        return supporterInfo;
    }
}
