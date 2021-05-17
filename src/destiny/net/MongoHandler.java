package destiny.net;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MongoHandler {
	
	private static MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://destinyUser:UazaArzUD6uu5rIC@destinyserver0-shard-00-00.pcxs3.mongodb.net:27017,destinyserver0-shard-00-01.pcxs3.mongodb.net:27017,destinyserver0-shard-00-02.pcxs3.mongodb.net:27017/destinyRollDatabase?ssl=true&replicaSet=atlas-fdt3yf-shard-0&authSource=admin&w=majority"));
	private static MongoDatabase db = mongoClient.getDatabase("destinyRollDatabase");
	private static MongoCollection<Document> characterCol = db.getCollection("characterData");
	private static MongoCollection<Document> userCol = db.getCollection("userData");
	
	private MongoHandler() {}
	
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
		
		userCol.insertOne(new Document("_id", userName).append("pswd", pswd).append("characters", chars));
		
	}
	
	public static Document rollRandomCharacter() {
		
		double val = ThreadLocalRandom.current().nextDouble(0, 100);
		
		double[][] nums = {{12.5, 12.5, 12.5, 12.5}, {7, 7, 7, 7}, {4, 4, 4}, {2, 2, 2, 2}, {1, 1, 1}, {0.5, 0.5}};
		double sum = 0;
		int count = 0;
		
		for (double[] i  : nums) {
			
			for (double d : i) {
				
				sum += d;
				count++;
				
				if (val < sum) {
					
					return characterCol.find(eq("_id", count)).first();
					
				}
				
			}
			
		}
		
		return null;
		
	}

}
