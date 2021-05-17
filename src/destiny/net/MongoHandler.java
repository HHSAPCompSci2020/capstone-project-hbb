package destiny.net;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

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
		
		userCol.insertOne(new Document("_id", userName).append("pswd", pswd));
		
	}
	
	

}
