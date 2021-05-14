package destiny.net;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class MongoHandler {
	
	private static MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://destinyUser:UazaArzUD6uu5rIC@destinyserver0.pcxs3.mongodb.net/destinyRollDatabase?retryWrites=true&w=majority"));
	private static MongoDatabase db = mongoClient.getDatabase("destinyRollDatabase");
	private static MongoCollection<Document> col = db.getCollection("userData");
	
	private MongoHandler() {}
	
	public static Document getStatDoc(int id) {
		
		return col.find(eq("_id", id)).first();
		
	}

}
