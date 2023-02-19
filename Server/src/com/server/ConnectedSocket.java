package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import dto.RequestDto;
import dto.ResponseDto;
import dto.SendMessageDto;
import dto.SendRoomDto;
import entity.Room;
import entity.User;
import lombok.Getter;
import repository.RoomRepository;
import repository.UserRepository;

public class ConnectedSocket extends Thread {
	@Getter
	private Socket socket;	
	private InputStream inputStream;
	private OutputStream outputStream;
	private Gson gson;
	
	@Getter
	private User user;
	
	public ConnectedSocket(Socket socket) {
		this.socket = socket;
		gson = new Gson();
	}
	
	@Override
	public void run() {
		try {
			inputStream = socket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));


			while(true) {
				String request = in.readLine();
				RequestDto requestDto = gson.fromJson(request, RequestDto.class);

				switch(requestDto.getResource()) {
					case "login":
						user = new User(requestDto.getBody());
						UserRepository.getInstance().addUser(user);
						sendResponse(requestDto.getResource(), gson.toJson(user));
						break;
						
					case "createRoom":
						Room createdRoom = new Room(requestDto.getBody());
						SendRoomDto sendRoomDto = new SendRoomDto(createdRoom);
						createdRoom.getSocketList().add(this);
						RoomRepository.getInstance().addRoom(createdRoom);
						sendResponse(requestDto.getResource(), gson.toJson(sendRoomDto));
						break;
						
					case "getRoomList":
						sendRoomList(requestDto.getResource());
						break;
						
					case "joinRoom":
						int roomId = Integer.parseInt(requestDto.getBody());
						Room joinRoom = RoomRepository.getInstance().findRoomByRoomId(roomId);
						joinRoom.getSocketList().add(this);
						
						
						SendRoomDto joinRoomDto = new SendRoomDto(joinRoom);
						sendResponse(requestDto.getResource(), gson.toJson(joinRoomDto));
						sendUserListToAll(joinRoom);
						break;
						
					case "sendMessage":
						SendMessageDto sendMessageDto = gson.fromJson(requestDto.getBody(), SendMessageDto.class);
						User fromUser = UserRepository.getInstance().findUserByUserId(sendMessageDto.getUserId());
						Room toRoom = RoomRepository.getInstance().findRoomByRoomId(sendMessageDto.getRoomId());
						String message = sendMessageDto.getMessage();
						
						sendMessageToAll(fromUser.getUsername(), toRoom, message);
						break;
			
				}
			}	
		} catch (IOException e) {
		}
		
		
	}
	
	private void sendResponse(String resource, String body) throws IOException {
		ResponseDto responseDto = new ResponseDto(resource, body);
		outputStream = socket.getOutputStream();
		PrintWriter out = new PrintWriter(outputStream, true);
		out.println(gson.toJson(responseDto));
	}
	
	private void sendRoomList(String resource) throws IOException {
		List<Room> roomList = RoomRepository.getInstance().getRoomList();
		List<String> roomListToSend = new ArrayList<>();
		if(!roomList.isEmpty()) {
			for(Room room : roomList) {
				SendRoomDto sendRoomDto = new SendRoomDto(room);
				roomListToSend.add(gson.toJson(sendRoomDto));
			}
			sendResponse(resource, gson.toJson(roomListToSend));
			
		} else if (roomList.isEmpty()) {
			sendResponse("emptyRoom", "채팅방이 존재하지 않습니다.");
			
		}

	}
	
	private void sendUserListToAll(Room room) throws IOException {
		List<ConnectedSocket> socketList = room.getSocketList();
		List<User> userList = new ArrayList<>();
		
		for(ConnectedSocket socket : socketList) {
			userList.add(socket.getUser());
		}
		
		ResponseDto responseDto = new ResponseDto("newUserJoin", gson.toJson(userList));
		
		for(ConnectedSocket socket : socketList) {
			OutputStream outputStream = socket.getSocket().getOutputStream();
			PrintWriter out = new PrintWriter(outputStream, true);
			out.println(gson.toJson(responseDto));
		}
		
	}
	
	private void sendMessageToAll(String username, Room room, String message) throws IOException {
		List<ConnectedSocket> socketList = room.getSocketList();
		
		ResponseDto responseDto = new ResponseDto("sendMessage", username + ":" + message);
		
		for(ConnectedSocket socket : socketList) {
			OutputStream outputStream = socket.getSocket().getOutputStream();
			PrintWriter out = new PrintWriter(outputStream, true);
			out.println(gson.toJson(responseDto));
		}
	}

}
