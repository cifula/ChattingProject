package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import dto.RequestDto;
import entity.User;
import repository.UserRepository;

public class ConnectedSocket extends Thread {
	private Socket socket;
	private static List<ConnectedSocket> socketList = new ArrayList<>();	
	private InputStream inputStream;
	private Gson gson;
	
	public ConnectedSocket(Socket socket) {
		this.socket = socket;
		gson = new Gson();
		socketList.add(this);
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
					case "user":
						User user = new User(requestDto.getBody());
						UserRepository.getInstance().addUser(user);
				}
				
				
				
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
