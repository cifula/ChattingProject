package entity;

import java.util.ArrayList;
import java.util.List;

import com.server.ConnectedSocket;

import lombok.Data;

@Data
public class Room {
	private static int ai = 0;
	private int roomId = 0;
	private String roomname;
	private List<ConnectedSocket> socketList;
	
	public Room(String roomname) {
		this.roomname = roomname;
		this.socketList = new ArrayList<>();
		roomId = ai;
		ai++;
	}

}
