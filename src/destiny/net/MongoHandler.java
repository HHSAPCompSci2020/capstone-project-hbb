package destiny.net;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class MongoHandler {
	
	private static MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://destinyAdmin:<password>@destinyserver0.pcxs3.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"));
	private static MongoDatabase db = mongoClient.getDatabase("destinyRollDatabase");
	private static MongoCollection<Document> col = db.getCollection("");
	
	private MongoHandler() {}
	
	public static Document getStatDoc(int id) {
		
		return col.find(eq("_id", id)).first();
		
	}

}
