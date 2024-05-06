package manager;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Bodega;
import model.Campo;
import model.Entrada;
import model.Grapevine;
import model.Inputdata;
import model.Vid;
import model.Vineyard;
import model.Winery;
import utils.TipoVid;

public class Manager {
	private static Manager manager;
	private ArrayList<Inputdata> entradas;
	private Session session;
	private Transaction tx;
	private Bodega b;
	private Campo c;
	MongoCollection<Document> collection;

	
	private int bodegaId=1;
	private int campoId=1;
	private int vidId=1;
	daoDbImplMongo mongodb= new daoDbImplMongo();
	MongoDatabase database;
	private Manager () {
		this.entradas = new ArrayList<>();
	}
	
	public static Manager getInstance() {
		if (manager == null) {
			manager = new Manager();
		}
		return manager;
	}
	
	private void createSession() {
		
		mongodb.connect();
		database= mongodb.database;
		this.entradas= mongodb.getInputData();
		System.out.println(entradas);
	}

	public void init() {
		createSession();
		manageActions();
		
	
	}

	private void manageActions() {
		for (Inputdata entrada : this.entradas) {
			try {
				System.out.println(entrada.getAction());
				switch (entrada.getAction().toUpperCase().split(" ")[0]) {
					case "B":
						addWinery(entrada.getAction().split(" "));
						break;
					case "C":
						addVineyard(entrada.getAction().split(" "));
						break;
					case "V":
						addGrapevine(entrada.getAction().split(" "));
						break;
					case "#":
						//vendimia();
						break;
					default:
						System.out.println("Instruccion incorrecta");
				}
			} catch (HibernateException e) {
				e.printStackTrace();
				if (tx != null) {
					tx.rollback();
				}
			}
		}
	}
	

	private void vendimia() {
		this.b.getVids().addAll(this.c.getVids());
		
		tx = session.beginTransaction();
		session.save(b);
		
		tx.commit();
	}

	public void addWinery (String [] split) {
		collection =database.getCollection("wineries");
		
		Document document = new Document().append("name", split[1]);
		collection.insertOne(document);
	}
	
	
	public void addVineyard(String [] split) {
		collection =database.getCollection("wineries");
		Document lastWinery=collection.find().sort(new Document("id", -1)).first();
		
		
		
		collection =database.getCollection("vineyards");
		Document document = new Document().append("name", lastWinery.getObjectId("_id")).append("collected", false)
		.append("winery", lastWinery);
		
		collection.insertOne(document);
	}
	
	public void addGrapevine (String [] split) {
		collection = database.getCollection("vineyards");
		Document lastVineyard = collection.find().sort(new Document("id", -1)).first();
		
		collection =database.getCollection("grapevines");
		Document document = new Document().append("type", split[1].toString()).append("quantity", split[2]).append("vineyard", lastVineyard);
		collection.insertOne(document);
		
		Document document2 = new Document().append("type", split[1].toString()).append("quantity", split[2]);
		collection = database.getCollection("vineyards");
		
		
		
		Document update = new Document ("$push", new Document("grapevines", document2));
		collection.updateOne(document2, update);
	}

	private void getEntrada() {
		tx = session.beginTransaction();
		Query q = session.createQuery("select e from Entrada e");
		this.entradas.addAll(q.list());
		tx.commit();
	}

	private void showAllCampos() {
		tx = session.beginTransaction();
		Query q = session.createQuery("select c from Campo c");
		List<Campo> list = q.list();
		for (Campo c : list) {
			System.out.println(c);
		}
		tx.commit();
	}

	
}
