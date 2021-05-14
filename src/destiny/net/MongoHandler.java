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
    private static MongoCollection<Document> col = db.getCollection("userData");
	
	private MongoHandler() {}
	
	public static Document getStatDoc(int id) {
		
		return col.find(eq("_id", id)).first();
			
	}

}
