package destiny.net;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

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
				.append("last_update", new Date(0)).append("currency", 0).append("stamina", 100)
				.append("levels_unlocked", 1));

	}

	private static void registerNewCharacter(int id, String userName) {

		userCol.updateOne(eq("_id", userName), new Document("$push", new Document("characters", id)));

	}

	public static Document rollRandomCharacter(String userName) {

		double val = ThreadLocalRandom.current().nextDouble(0, 100);

		FindIterable<Document> nums = characterCol.find().sort(new Document("drop", -1));
		double sum = 0;

		int count = 0;

		try (MongoCursor<Document> cursor = nums.iterator()) {
			while (cursor.hasNext()) {
				double i = cursor.next().getDouble("drop");
				sum += i;
				count++;

				if (val < sum) {

					Document doc = characterCol.find(eq("_id", count)).first();

					@SuppressWarnings("unchecked")
					ArrayList<Integer> characters = getUserDoc(userName).get("characters",
							new ArrayList<Integer>().getClass());

					int id = doc.getInteger("_id");

					if (characters.contains(id))
						return null;

					registerNewCharacter(id, userName);

					return doc;

				}
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

	public static void progressLevel(String userName) {

		userCol.updateOne(new Document("_id", userName), new Document("$inc", new Document("levels_unlocked", 1)));

	}

//	public static void setupCharacterStats() {
//
//		FindIterable<Document> chars = characterCol.find().sort(new Document("drop", -1));
//
//		try (MongoCursor<Document> cursor = chars.iterator()) {
//			while (cursor.hasNext()) {
//				Document d = cursor.next();
//				if (d.getString("rarity").equals("C")) {
//					int num = 0;
//					randomizeCharacterDoc(d, num);
//				} else if (d.getString("rarity").equals("UC")) {
//					int num = 1;
//					randomizeCharacterDoc(d, num);
//				} else if (d.getString("rarity").equals("R")) {
//					int num = 2;
//					randomizeCharacterDoc(d, num);
//				} else if (d.getString("rarity").equals("SR")) {
//					int num = 3;
//					randomizeCharacterDoc(d, num);
//				} else if (d.getString("rarity").equals("UR")) {
//					int num = 4;
//					randomizeCharacterDoc(d, num);
//				} else if (d.getString("rarity").equals("L")) {
//					int num = 5;
//					randomizeCharacterDoc(d, num);
//				}
//
//				characterCol.replaceOne(eq("_id", d.getInteger("_id")), d);
//			}
//		}
//
//	}
	
//	private static void randomizeCharacterDoc(Document d, int rarity) {
//		
//		int baseH = 70, baseA = 10, baseD = 30, baseM = 90;
//		int dif = 20;
//		
//		d.put("health", ThreadLocalRandom.current().nextInt(baseH + dif * rarity, baseH + dif * (rarity + 1)));
//		d.put("attack", ThreadLocalRandom.current().nextInt(baseA + dif*rarity, baseA + dif * (rarity + 1)));
//		d.put("defense", ThreadLocalRandom.current().nextInt(baseD + dif*rarity, baseD + dif * (rarity + 1)));
//		d.put("mp", ThreadLocalRandom.current().nextInt(baseM + dif*rarity, baseM + dif * (rarity + 1)));
//		
//	}

//	public static void resetUsers() {
//		
//		ArrayList<Integer> chars = new ArrayList<>();
//		chars.add(1);
//		chars.add(2);
//		chars.add(3);
//		
//		userCol.updateMany(new Document(), new Document("$set", new Document("characters", chars).append("last_update", new Date(0)).append("currency", 0).append("stamina", 100).append("levels_unlocked", 1)));
//		
//	}

}
