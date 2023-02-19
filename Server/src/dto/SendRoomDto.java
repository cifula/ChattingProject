package dto;

import java.util.ArrayList;
import java.util.List;

import com.server.ConnectedSocket;

import entity.Room;
import entity.User;
import lombok.Data;

@Data
public class SendRoomDto {
	private int roomId = 0;
	private String roomname;
	private List<User> userList;
	
	public SendRoomDto(Room room) {
		roomId = room.getRoomId();
		roomname = room.getRoomname();
		userList = new ArrayList<>();
		for(ConnectedSocket socket : room.getSocketList()) {
			userList.add(socket.getUser());
		}
	}
}
