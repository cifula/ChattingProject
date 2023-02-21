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
	private User roomMaster;
	private List<ConnectedSocket> socketList;
	
	public Room(String roomname, User roomMaster) {
		this.roomname = roomname;
		this.roomMaster = roomMaster;
		this.socketList = new ArrayList<>();
		roomId = ai;
		ai++;
	}
	

}
