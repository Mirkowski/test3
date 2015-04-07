package com.mirkowski;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;


public class JsonBulider {
	
	public static String buildJsonUsersData(Set<String> UserNames, String messageType){
		Iterator<String> iterator = UserNames.iterator();
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		while(iterator.hasNext())jsonArrayBuilder.add((String)iterator.next());
		JsonObject jsonObject = Json.createObjectBuilder().add("messageType", messageType)
														  .add("users", jsonArrayBuilder)
														  .add("lenght", UserNames.size()) // json widzi w widoku jako int
														  .build();
		System.out.println("jO "+jsonObject.toString());
		return jsonObject.toString();
	}
	
	public static String buildJsonMessageData(ChatMessage chatMessage){
		//public static String buildJsonMessageData(String username, String message){
		//JsonObject jsonObject = Json.createObjectBuilder().add("message", username + ": " + message).build();
		JsonObject jsonObject = Json.createObjectBuilder().add("messageType", chatMessage.getClass().getSimpleName())
														  .add("name", chatMessage.getName())
														  .add("message", chatMessage.getMessage())
														 // .add("location", chatMessage.getLocation())
														  .build(); // return jO toString
		StringWriter stringWriter = new StringWriter();
		try(JsonWriter jsonWriter = Json.createWriter(stringWriter)){jsonWriter.write(jsonObject);}
		System.out.println("JO-Message: "+stringWriter.toString());
		return stringWriter.toString();
	}
	
	
}
