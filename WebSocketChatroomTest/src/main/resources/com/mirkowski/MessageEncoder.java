package com.mirkowski;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Message message) throws EncodeException {
		String returnString = null;
		
		if(message instanceof ChatMessage){
			ChatMessage chatMessage = (ChatMessage) message;
			returnString = JsonBulider.buildJsonMessageData(chatMessage);
			
		} else if(message instanceof UserMessage){
			UserMessage userMessage = (UserMessage) message;
			returnString = JsonBulider.buildJsonUsersData(userMessage.getUsers(), userMessage.getClass().getSimpleName());
		}
		
		return returnString;
	}

}
