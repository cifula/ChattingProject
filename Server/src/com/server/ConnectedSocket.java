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
import entity.Room;
import entity.User;
import repository.RoomRepository;
import repository.UserRepository;

public class ConnectedSocket extends Thread {
	private Socket socket;	
	private InputStream inputStream;
	private OutputStream outputStream;
	private Gson gson;
	
	private String username;
	
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
					case "join":
						User user = new User(requestDto.getBody());
						UserRepository.getInstance().addUser(user);
						username = user.getUsername();
						sendResponse(requestDto.getResource(), "join");
						break;
						
					case "createRoom":
						Room room = new Room(requestDto.getBody());
						RoomRepository.getInstance().addRoom(room);
						break;
						
					case "getRoomList":
						sendRoomList(requestDto.getResource());
						break;
			
				}
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void sendResponse(String resource, String body) throws IOException {
		ResponseDto responseDto = new ResponseDto(resource, body);
		outputStream = socket.getOutputStream();
		PrintWriter out = new PrintWriter(outputStream, true);
		out.println(gson.toJson(responseDto));
	}
	
	private void sendRoomList(String resource) throws IOException {
		List<Room> roomList = RoomRepository.getRoomList();
		List<String> roomListToSend = new ArrayList<>();
		
		for(Room room : roomList) {
			roomListToSend.add(gson.toJson(room));
		}
				
		
		sendResponse(resource, gson.toJson(roomListToSend));
			
		
		
	}

}
