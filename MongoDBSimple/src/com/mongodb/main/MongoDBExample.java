package com.mongodb.main;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.model.User;

public class MongoDBExample {
	public static void main(String[] args) throws UnknownHostException {
		User user = createUser();
		DBObject doc = createDBObject(user);
		
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("mongoUsers");
		
		DBCollection col = db.getCollection("users");
		
		//Creamos usuarios
		WriteResult res = col.insert(doc); //insertamos el usuario
		System.out.println(res.getUpsertedId()); //Saca el id del usuario insertado
		System.out.println(res.getN());
		
		System.out.println(res.isUpdateOfExisting()); //comprueba si existe el usuario
		System.out.println(res.getLastConcern());
		
		//read example (es un select en mysql)
		DBObject query = BasicDBObjectBuilder.start().add("_id", user.getId()).get();
		DBCursor cursor = col.find(query);
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
		
		//update (actualizamos datos)
		//user.setName("Risa");
		doc = createDBObject(user);
		res = col.update(query, doc);
		System.out.println(res.getUpsertedId());
		System.out.println(res.getN());
		System.out.println(res.isUpdateOfExisting());
		System.out.println(res.getLastConcern());
		
		//delete (borramos)
//		res = col.remove(query);
//		System.out.println(res.getUpsertedId());
//		System.out.println(res.getN());
//		System.out.println(res.isUpdateOfExisting());
//		System.out.println(res.getLastConcern());
		
		//cerramos conexion
		mongo.close();
		
	}
	//implementamos la creacion de los atributos de la coleccion(tabla) usuarios
	private static DBObject createDBObject(User user) {
		// TODO Auto-generated method stub
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
		docBuilder.append("_id", user.getId());
		docBuilder.append("name", user.getName());
		docBuilder.append("role", user.getRole());
		docBuilder.append("isEmployee", user.isEmployee());
		return docBuilder.get();
	}
	
	//Creamos usuario
	private static User createUser() {
		// TODO Auto-generated method stub
		User u = new User();
		u.setId(4);
		u.setName("Laika");
		u.setRole("CEO");
		u.setEmployee(true);
		return u;
	}

}
