package com.mirkowski;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message decode(String jsonmessage) throws DecodeException {
		ChatMessage chatMessage = new ChatMessage();
		JsonObject jsonObject = Json.createReader(new StringReader(jsonmessage)).readObject();
		System.out.println("JsonObject: "+jsonObject.toString() + " Jsonmessage: " + jsonmessage);
		chatMessage.setMessage(jsonObject.getString("message"));
		//chatMessage.setLocation(jsonObject.getString("location"));//err
		return chatMessage;
	}

	@Override
	public boolean willDecode(String jsonmessage) {
		boolean flag = true;
		try {Json.createReader(new StringReader(jsonmessage)).readObject();} 
		catch (Exception e) { flag = false;}
		return flag;
	}

}
