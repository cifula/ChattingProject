package com.client;

import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

import javax.swing.JLabel;

import com.client.Repository.RoomRepository;
import com.client.dto.ResponseDto;
import com.client.entity.ConnectedUser;
import com.client.entity.Room;
import com.client.entity.User;
import com.client.panel.ChatroomPanel;
import com.client.panel.MainPanel;
import com.client.panel.MenuPanel;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientRecive extends Thread{
	
	private final Socket socket;
	private InputStream inputStream;
	private Gson gson;
	private CardLayout mainCard;
	
	@Override
	public void run() {
		try {
			inputStream = socket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			gson = new Gson();
			
		
			
			while(true) {
				String response = in.readLine();
				ResponseDto responseDto = gson.fromJson(response, ResponseDto.class);
				switch(responseDto.getResource()) {
					case "login":
						User loginUser = gson.fromJson(responseDto.getBody(), User.class);
						ConnectedUser.getInstance().setUser(loginUser);
						mainCard = MainPanel.getMainCard();
						mainCard.show(MainPanel.getInstance(), "menuPanel");
						break;
						
					case "createRoom":
						Room createdRoom = gson.fromJson(responseDto.getBody(), Room.class);
						RoomRepository.getInstance().addRoom(createdRoom);
						ChatroomPanel.getInstance().setRoom(createdRoom);
						ChatroomPanel.getInstance().getRoomnameLabel().setText(createdRoom.getRoomname());
						mainCard.show(MainPanel.getInstance(), "chatroomPanel");
						break;
						
					case "getRoomList":
						MenuPanel.getInstance().getLs().clear();
						RoomRepository.getInstance().clear();
						List<String> rooms = gson.fromJson(responseDto.getBody(), List.class);
						
						for (String body : rooms) {
							RoomRepository.getInstance().addRoom(gson.fromJson(body, Room.class));
						}
						
						List<Room> roomList = RoomRepository.getInstance().getRoomList();
						
						for (Room room : roomList) {
							MenuPanel.getInstance().getLs().addElement(room.getRoomname());
						}
						
						break;
						
					case "emptyRoom":
						MenuPanel.getInstance().getLs().addElement(responseDto.getBody());
						break;
							
					case "joinRoom":
						Room joinRoom = gson.fromJson(responseDto.getBody(), Room.class);
						ChatroomPanel.getInstance().setRoom(joinRoom);
						mainCard.show(MainPanel.getInstance(), "chatroomPanel");
						break;
						
					case "newUserJoin":
						List<User> userList = gson.fromJson(responseDto.getBody(), List.class);
						ChatroomPanel.getInstance().getRoom().setUserList(userList);
						break;
						
					case "sendMessage":
						ChatroomPanel.getInstance().getContentArea().append(responseDto.getBody() + '\n');
						break;
				}
			}
			

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
