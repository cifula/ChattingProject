package entity;

import lombok.Data;

@Data
public class Room {
	private static int ai = 0;
	private int roomId = 0;
	private String roomname;
	
	public Room(String roomname) {
		this.roomname = roomname;
		roomId = ai;
		ai++;
	}

}
