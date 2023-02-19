package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {
	
	
	public static void main(String[] args) {
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
			e.printStackTrace();
			
		} finally {
			System.out.println("서버 종료");
		}

	}
}
