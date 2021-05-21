package destiny.net;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import destiny.assets.Player;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Projections.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class MongoHandler {

	private static MongoClient mongoClient = new MongoClient(new MongoClientURI(
			"mongodb://destinyUser:UazaArzUD6uu5rIC@destinyserver0-shard-00-00.pcxs3.mongodb.net:27017,destinyserver0-shard-00-01.pcxs3.mongodb.net:27017,destinyserver0-shard-00-02.pcxs3.mongodb.net:27017/destinyRollDatabase?ssl=true&replicaSet=atlas-fdt3yf-shard-0&authSource=admin&w=majority"));
	private static MongoDatabase db = mongoClient.getDatabase("destinyRollDatabase");
	private static MongoCollection<Document> characterCol = db.getCollection("characterData");
	private static MongoCollection<Document> userCol = db.getCollection("userData");

	private MongoHandler() {
	}

	/**
	 * 
	 * Gets the bson document corresponding to the id number of the revolutionary
	 * 
	 * @param id The id number of the revolutionary
	 * @return The bson Document that is queried from mongodb
	 */
	public static Document getStatDoc(int id) {

		return characterCol.find(eq("_id", id)).first();

	}

	public static Document getUserDoc(String userName) {

		return userCol.find(eq("_id", userName)).first();

	}

	public static boolean checkUserLogin(String userName, String pswd) {

		return userCol.find(eq("_id", userName)).first().getString("pswd").equals(pswd);

	}

	public static void addUser(String userName, String pswd) {

		ArrayList<Integer> chars = new ArrayList<>();
		chars.add(1);
		chars.add(2);
		chars.add(3);

		userCol.insertOne(new Document("_id", userName).append("pswd", pswd).append("characters", chars)
				.append("last_update", new Date(0)).append("currency", 0).append("stamina", 100));

	}

	private static void registerNewCharacter(int id, String userName) {

		userCol.updateOne(eq("_id", userName), new Document("$push", new Document("characters", id)));

	}

	public static Document rollRandomCharacter(String userName) {

		double val = ThreadLocalRandom.current().nextDouble(0, 100);

		FindIterable<Document> nums = characterCol.find().sort(new Document("drop", -1));
		double sum = 0;

		int count = 0;

		for (Document i : nums) {

			sum += i.getDouble("drop");
			count++;

			if (val < sum) {

				Document doc = characterCol.find(eq("_id", count)).first();

				@SuppressWarnings("unchecked")
				ArrayList<Integer> characters = getUserDoc(userName).get("characters", new ArrayList<Integer>().getClass());

				int id = doc.getInteger("_id");
				
				if (characters.contains(id))
					return null;

				registerNewCharacter(id, userName);
				Player.getCharacters().add(id);

				return doc;

			}

		}

		return null;

	}

	public static void updateStamina(String userName, int stamina, Date now) {

		userCol.updateOne(new Document("_id", userName),
				new Document("$set", new Document("stamina", stamina).append("last_update", now)));

	}

	public static void updateCurrency(String userName, int currency) {

		userCol.updateOne(new Document("_id", userName), new Document("$set", new Document("currency", currency)));

	}

//	public static void resetUsers() {
//		
//		ArrayList<Integer> chars = new ArrayList<>();
//		chars.add(1);
//		chars.add(2);
//		chars.add(3);
//		
//		userCol.updateMany(new Document(), new Document("$set", new Document("characters", chars).append("last_update", new Date(0)).append("currency", 0).append("stamina", 100)));
//		
//	}

}
