package repository;

import java.util.ArrayList;
import java.util.List;

import entity.Room;
import lombok.Getter;

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
	
	public void removeRoom(Room room) {
		roomList.remove(room);
	}
	
	public Room findRoomByRoomId(int roomId) {
		for(Room room : roomList) {
			if(room.getRoomId() == roomId) {
				return room;
			}
		}
		return null;
	}

}
