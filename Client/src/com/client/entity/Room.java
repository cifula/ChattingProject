package com.client.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Room {
	private int roomId;
	private String roomname;
	
	private List<User> userList;
	
	public Room() {
		this.userList = new ArrayList<>();
	}
	
}
