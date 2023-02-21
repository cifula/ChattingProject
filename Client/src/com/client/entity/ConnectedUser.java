package com.client.entity;

import lombok.Data;

@Data
public class ConnectedUser {
	
	private static ConnectedUser instance;
	
	public static ConnectedUser getInstance() {
		if(instance == null) {
			instance = new ConnectedUser();
		}
		return instance;
	}
	
	private ConnectedUser() {
		this.user = new User();
		this.room = new Room();
	}
	
	private User user;
	
	private Room room;

}
