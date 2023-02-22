package com.client;

import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

import javax.swing.DefaultListModel;

import com.client.Repository.RoomRepository;
import com.client.dto.ResponseDto;
import com.client.entity.ConnectedUser;
import com.client.entity.Room;
import com.client.entity.RoomPanel;
import com.client.entity.User;
import com.client.frame.MainFrame;
import com.client.panel.ChattingroomListPanel;
import com.client.panel.ChattingroomPanel;
import com.client.panel.MainPanel;
import com.client.panel.UserListPanel;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientRecive extends Thread{
	
	private final Socket socket;
	private InputStream inputStream;
	private Gson gson;
	private CardLayout mainCard;
	private DefaultListModel<String> userListModel;
	private Room room;
	
	@Override
	public void run() {
		try {
			inputStream = socket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			gson = new Gson();
			userListModel = new DefaultListModel<>();
			room = new Room();
			
			while(true) {
				String response = in.readLine();
				ResponseDto responseDto = gson.fromJson(response, ResponseDto.class);
				switch(responseDto.getResource()) {
					case "login":
						mainCard = MainPanel.getMainCard();
						User loginUser = gson.fromJson(responseDto.getBody(), User.class);
						ConnectedUser.getInstance().setUser(loginUser);
						mainCard.show(MainPanel.getInstance(), "chattingroomListPanel");
						break;
						
					case "updateRoomList":
						ChattingroomListPanel.getInstance().getRoomListModel().clear();
						RoomRepository.getInstance().clear();
						List<String> rooms = gson.fromJson(responseDto.getBody(), List.class);
						
						// RoomRepository에 RoomList 추가
						for (String body : rooms) {
							RoomRepository.getInstance().addRoom(gson.fromJson(body, Room.class));
						}
						
						
						List<Room> roomList = RoomRepository.getInstance().getRoomList();
						for (Room room : roomList) {
							ChattingroomListPanel.getInstance().getRoomListModel().addElement(new RoomPanel(room));
						}
						
						ChattingroomListPanel.getInstance().getRoomList().setModel(ChattingroomListPanel.getInstance().getRoomListModel());
						
						break;
						
					case "emptyRoom":
//						ChattingroomListPanel.getInstance().getRoomListModel().addElement(responseDto.getBody());
						break;
													
					case "joinRoom":
						room = gson.fromJson(responseDto.getBody(), Room.class);
						
						userListModel.clear();
						ChattingroomPanel.getInstance().getContentArea().setText("");
						
						ChattingroomPanel.getInstance().getRoomnameLabel().setText(room.getRoomname());
						UserListPanel.getInstance().getRoomnameLabel().setText(room.getRoomname());
						userListUpdate(room);
						mainCard.show(MainPanel.getInstance(), "chattingroomPanel");
						break;
						
					case "updateUserList":
						userListModel.clear();
						room = gson.fromJson(responseDto.getBody(), Room.class);
						userListUpdate(this.room);
						break;
						
					case "sendMessage":
						ChattingroomPanel.getInstance().getContentArea().append(responseDto.getBody() + '\n');
						break;
						
					case "masterExit":
						MainFrame.getInstance().masterExit();
						MainPanel.getMainCard().show(MainPanel.getInstance(), "chattingroomListPanel");
				}
			}
			

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized void userListUpdate(Room room) {
		for(User user : room.getUserList()) {
			userListModel.addElement(user.getUsername());
		}
		ChattingroomPanel.getInstance().setRoom(room);
		UserListPanel.getInstance().setRoom(room);
		UserListPanel.getInstance().getUserList().setModel(userListModel);
		
		UserListPanel.getInstance().getUserList().setSelectedIndex(0);
		if(UserListPanel.getInstance().getUserList().getSelectedValue().isBlank()) {
			System.out.println("선택된 값 없음!!");
		}
	}

}
