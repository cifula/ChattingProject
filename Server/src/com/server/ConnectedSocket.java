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
	private static List<ConnectedSocket> connectedSocketList = new ArrayList<>();
	private RoomRepository roomRepository;
	
	@Getter
	private User user;
	
	public ConnectedSocket(Socket socket) {
		this.socket = socket;
		roomRepository = RoomRepository.getInstance();
		connectedSocketList.add(this);
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
						
					case "updateRoomList":
						sendResponse("updateRoomList", getRoomListToSend());
						break;
						
					case "createRoom":
						// 새로운 Room 생성
						Room createdRoom = new Room(requestDto.getBody(), user);
						createdRoom.getSocketList().add(this);
						RoomRepository.getInstance().addRoom(createdRoom);
						
						// 새로 생성된 Room 모든 User에게 전달
						sendResponseTo("updateRoomList", getRoomListToSend(), connectedSocketList);
						
						// 생성한 유저에게 생성된 Room 전달 및 생성된 Room으로 joinRoom
						sendResponse("joinRoom", getRoomToSend(createdRoom));
						break;
						
					case "joinRoom":
						// RoomId로 joinRoom 할 Room 찾기
						int joinRoomId = Integer.parseInt(requestDto.getBody());
						Room joinRoom = roomRepository.findRoomByRoomId(joinRoomId);
						
						// joinRoom 할 Room에 joinRoom 한 User 추가
						joinRoom.getSocketList().add(this);
						
						// joinRoom 한 User에게 "joinRoom" 및 Update된 Room 전송 
						sendResponse(requestDto.getResource(), getRoomToSend(joinRoom));
						
						// joinRoom 에 입장해있던 기존 User 들에게 "newUserJoin" 및 Update된 Room 전송
						sendResponseTo("updateUserList", getRoomToSend(joinRoom), joinRoom.getSocketList());
						break;
						
					case "sendMessage":
						SendMessageDto sendMessageDto = gson.fromJson(requestDto.getBody(), SendMessageDto.class);
						String message = user.getUsername() + ":" + sendMessageDto.getMessage();
						Room toRoom = roomRepository.findRoomByRoomId(sendMessageDto.getRoomId());
						
						sendResponseTo(requestDto.getResource(), message, toRoom.getSocketList());
						break;
						
					case "exitRoom":
						int exitRoomId = Integer.parseInt(requestDto.getBody());
						Room exitRoom = roomRepository.findRoomByRoomId(exitRoomId);
						
						if(!exitRoom.getRoomMaster().equals(user)) {
							exitRoom.getSocketList().remove(this);
							sendResponseTo("updateUserList", getRoomToSend(exitRoom), exitRoom.getSocketList());
						} else if(exitRoom.getRoomMaster().equals(user)) {
							RoomRepository.getInstance().removeRoom(exitRoom);
							sendResponseTo("updateRoomList", getRoomListToSend(), connectedSocketList);
						}
						
						
			
				}
			}	
		} catch (IOException e) {
			
		}
		
		
	}
	
	private void sendResponse(String resource, String body) {
		ResponseDto responseDto = new ResponseDto(resource, body);
		try {
			outputStream = socket.getOutputStream();
			PrintWriter out = new PrintWriter(outputStream, true);
			out.println(gson.toJson(responseDto));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getRoomListToSend() {
		List<Room> roomList = RoomRepository.getInstance().getRoomList();
		List<String> roomListToSend = new ArrayList<>();
	
		for(Room room : roomList) {
			SendRoomDto sendRoomDto = new SendRoomDto(room);
			roomListToSend.add(gson.toJson(sendRoomDto));
		}
		
		return gson.toJson(roomListToSend);
		
	}
	
	public String getRoomToSend(Room room) {
		SendRoomDto roomToSend = new SendRoomDto(room);
		return gson.toJson(roomToSend);
	}
	
	private void sendResponseTo(String resource, String body, List<ConnectedSocket> to) {
		for(ConnectedSocket connectedSocket : to) {
			connectedSocket.sendResponse(resource, body);
		}
	}
}
