package com.mirkowski;

import java.util.Set;

public class UserMessage implements Message {
	
	private Set<String> users = null;

	public UserMessage(Set<String> users) {
		this.users = users;
	}

	public Set<String> getUsers() {
		return users;
	}
	
	
	
}
