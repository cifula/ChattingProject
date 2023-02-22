package com.client.Repository;

import java.util.ArrayList;
import java.util.List;

import com.client.entity.Room;

import lombok.Getter;
import lombok.Setter;

public class RoomRepository {
	
	private static RoomRepository instance;
	
	public static RoomRepository getInstance() {
		if(instance == null) {
			instance = new RoomRepository();
		}
		return instance; 
	}
	
	private RoomRepository() {
		roomList = new ArrayList<>();
	}
	
	
	@Getter
	private List<Room> roomList;
	
	public void addRoom(Room room) {
		roomList.add(room);
	}
	
	public void clear() {
		roomList = new ArrayList<>();
	}
	



}
