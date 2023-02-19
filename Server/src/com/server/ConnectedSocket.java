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

import com.client.frame.MainFrame;
import com.google.gson.Gson;

import dto.MakeReqDto;
import dto.MakeRespDto;
import dto.RequestDto;
import dto.ResponseDto;
import lombok.Data;

@Data
public class ConnectedSocket extends Thread {
	private Socket socket = MainFrame.getSocket();
	private static List<ConnectedSocket> socketList = new ArrayList<>();	
	private InputStream inputStream;
	private Gson gson;
	
	private String roomname;
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
				case "login" :
					socketList.add(this);
				case "make" :
					MakeReqDto makeReqDto = gson.fromJson(requestDto.getBody(), MakeReqDto.class);
					roomname = makeReqDto.getRoomname();
					username = makeReqDto.getUsername();
					MakeRespDto makeRespDto = new MakeRespDto(username +"의 방: " + roomname);
					ResponseDto responseDto = new ResponseDto(requestDto.getResource(),gson.toJson(makeRespDto));
					for (ConnectedSocket connectedSocket : socketList) {
						OutputStream outputStream = connectedSocket.getSocket().getOutputStream();
						PrintWriter writer = new PrintWriter(outputStream,true);
						writer.print(gson.toJson(responseDto));
					}
					break;
				}
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
