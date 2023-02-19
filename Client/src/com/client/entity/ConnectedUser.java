package com.client.entity;

import lombok.Getter;
import lombok.Setter;

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
	
	@Getter
	@Setter
	private User user;
	
	@Getter
	@Setter
	private Room room;

}
