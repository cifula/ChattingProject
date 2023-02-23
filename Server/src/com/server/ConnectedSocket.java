package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
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
	private int roomId;
	
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
						
						// Room 생성 Message 전송
						sendResponseTo("sendMessage", "[알림] " + createdRoom.getRoomname() + "방이 생성되었습니다.", createdRoom.getSocketList());
						
						roomId = createdRoom.getRoomId();
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
						
						// joinMessage 전송
						sendResponseTo("sendMessage", "[알림] " + user.getUsername() + "님이 입장하였습니다.", joinRoom.getSocketList());
						
						roomId = joinRoom.getRoomId();
						break;
						
					case "sendMessage":
						// message 형태 전처리
						SendMessageDto sendMessageDto = gson.fromJson(requestDto.getBody(), SendMessageDto.class);
						String message = user.getUsername() + ":" + sendMessageDto.getMessage();
						
						// message 보낼 Room 찾기
						Room toRoom = roomRepository.findRoomByRoomId(sendMessageDto.getRoomId());
						
						// 찾은 Room에 "sendMessage", message 전송
						sendResponseTo(requestDto.getResource(), message, toRoom.getSocketList());
						break;
						
					case "exitRoom":
						// RoomId로 exitRoom 할 Room 찾기
						int exitRoomId = Integer.parseInt(requestDto.getBody());
						Room exitRoom = roomRepository.findRoomByRoomId(exitRoomId);

						exitRoom(exitRoom);
				}
			}	
		} catch (SocketException e){
			Room FTUserRoom = roomRepository.findRoomByRoomId(roomId);
			exitRoom(FTUserRoom);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	// Response 전송 메소드
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
	
	// roomList에 있는 모든 Room을 SendRoomDto로 변환하여 return하는 메소드
	private String getRoomListToSend() {
		List<Room> roomList = RoomRepository.getInstance().getRoomList();
		List<String> roomListToSend = new ArrayList<>();
	
		for(Room room : roomList) {
			SendRoomDto sendRoomDto = new SendRoomDto(room);
			roomListToSend.add(gson.toJson(sendRoomDto));
		}
		
		return gson.toJson(roomListToSend);
		
	}
	
	// Room을 SendRoomDto로 변환하여 return하는 메소드
	private String getRoomToSend(Room room) {
		SendRoomDto roomToSend = new SendRoomDto(room);
		return gson.toJson(roomToSend);
	}
	
	// to에 존재하는 모든 User에게 Response를 전송하는 메소드
	private void sendResponseTo(String resource, String body, List<ConnectedSocket> to) {
		for(ConnectedSocket connectedSocket : to) {
			connectedSocket.sendResponse(resource, body);
		}
	}
	
	// Room 나가기 메소드
	
	private void exitRoom(Room room) {
		if(!room.getRoomMaster().equals(user)) {
			// 해당 Room에서 exitRoom 하는 User를 삭제
				room.getSocketList().remove(this);
			// 해당 Room에 존재하는 User들에게 "updateUserList", Update된 Room을 전송	
				sendResponseTo("updateUserList", getRoomToSend(room), room.getSocketList());
				sendResponseTo("sendMessage", "[알림] " + user.getUsername() + "님이 퇴장하였습니다.", room.getSocketList());

			// exitRoom 하는 User가 RoomMaster일 경우
			} else if(room.getRoomMaster().equals(user)) {
				room.getSocketList().remove(this);
				sendResponseTo("masterExit", "pass", room.getSocketList());
			// 해당 Room을 RoomRepository에서 제거
				RoomRepository.getInstance().removeRoom(room);
			// 모든 User에게 "updateRommList", Update된 RoomList 전송
				sendResponseTo("updateRoomList", getRoomListToSend(), connectedSocketList);
		
			}
	}
}