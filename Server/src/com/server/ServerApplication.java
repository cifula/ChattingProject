package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import entity.Room;
import repository.RoomRepository;

public class ServerApplication {
	
	private Socket socket;
	
	public static void main(String[] args) {
		RoomRepository.getInstance().addRoom(new Room("111"));
		RoomRepository.getInstance().addRoom(new Room("222"));
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(9090);
			System.out.println("서버 실행");
			
			while(true) {
				Socket socket = serverSocket.accept();
				ConnectedSocket connectedSocket = new ConnectedSocket(socket);
				connectedSocket.start();
				
				
			}
			
		} catch (IOException e) {
			System.out.println("");
			
		} finally {
			System.out.println("서버 종료");
		}

	}
}
