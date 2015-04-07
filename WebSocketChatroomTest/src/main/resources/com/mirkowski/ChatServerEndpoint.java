package com.mirkowski;


import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;






import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/Chat", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class ChatServerEndpoint {
	private static Set<Session> chatroomUsers = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void handleOpen(Session userSession) throws IOException, EncodeException{
		chatroomUsers.add(userSession);
		Iterator<Session> iterator = chatroomUsers.iterator();
		while(iterator.hasNext()) iterator.next().getBasicRemote().sendObject(new UserMessage(getIds()));//err
	}
	
	@OnMessage
	public void handleMessage(Message incomingMessage, Session userSession) throws IOException, EncodeException{
		if(incomingMessage instanceof ChatMessage){
			ChatMessage incomingChatMessage = (ChatMessage) incomingMessage;
			ChatMessage outgoingChatMessage = new ChatMessage();
			String username = (String) userSession.getUserProperties().get("username");
			System.out.println("In "+incomingChatMessage.toString() + "Username: "+ username);
			if(username == null){
				userSession.getUserProperties().put("username", incomingChatMessage.getMessage());
				outgoingChatMessage.setName("System");
				outgoingChatMessage.setLocation("Poland");
				outgoingChatMessage.setMessage("you are now connected as : "+incomingChatMessage.getMessage());
				System.out.println("Out "+ outgoingChatMessage);
				userSession.getBasicRemote().sendObject(outgoingChatMessage);
			}else{
				outgoingChatMessage.setName(username);
				outgoingChatMessage.setMessage(incomingChatMessage.getMessage());
				System.out.println("Out "+ outgoingChatMessage);
				Iterator<Session> iterator = chatroomUsers.iterator();
				while(iterator.hasNext()) iterator.next().getBasicRemote().sendObject(outgoingChatMessage);
			}
			Iterator<Session> iterator = chatroomUsers.iterator();
			while(iterator.hasNext()) iterator.next().getBasicRemote().sendObject(new UserMessage(getIds())); //null pionter
		}
	}
	
	@OnClose
	public void handleClose(Session userSession) throws IOException, EncodeException{
		chatroomUsers.remove(userSession);
		Iterator<Session> iterator = chatroomUsers.iterator();
		while(iterator.hasNext()) iterator.next().getBasicRemote().sendObject(new UserMessage(getIds()));
	}
	
	@OnError
	public void handleError(Throwable t){
		t.printStackTrace();
	}
	
	public static Set<String> getIds(){
		HashSet<String> returnSet = new HashSet<String>();
		Iterator<Session> iterator = chatroomUsers.iterator();
		while(iterator.hasNext()) {
			try {
				returnSet.add(iterator.next().getUserProperties().get("username").toString());//err null pointer
			} catch (Exception e) {}
		}
		return returnSet;
	}
}
