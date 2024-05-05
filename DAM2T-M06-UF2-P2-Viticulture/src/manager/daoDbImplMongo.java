package manager;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Winery;
import model.Grapevine;
import model.Inputdata;
import model.Vineyard;

import org.bson.Document;
import java.util.ArrayList;




public class daoDbImplMongo {

	MongoCollection<Document> collection;
	MongoDatabase database;
	
	
	public void connect() {
		String uri = "mongodb://localhost:27017";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);
		
		database = mongoClient.getDatabase("viticultura");
		
		
	}
	
	
	
	public ArrayList<Inputdata> getInputData(){
		
		collection = database.getCollection("entrada");
		ArrayList<Inputdata> inputs = new ArrayList<>();
		
		for (Document document : collection.find()) {
			Inputdata input = new Inputdata();
			input.setAction (document.getString("instruccion"));
			input.set_id(document.getObjectId("_id"));
			inputs.add(input);
		}
		return inputs;
	}
	
	
	
}
