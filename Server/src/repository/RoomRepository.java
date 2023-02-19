package repository;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

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
		gson = new Gson();
		roomList = new ArrayList<>();
	}
	
	private Gson gson;
	
	@Getter
	private static List<Room> roomList;
		
	public void addRoom(Room room) {
		roomList.add(room);
	}

}
