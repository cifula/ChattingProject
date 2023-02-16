package com.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectedSocket extends Thread {
	private Socket socket;
	private static List<ConnectedSocket> socketList = new ArrayList<>();	
	
	public ConnectedSocket(Socket socket) {
		this.socket = socket;
		socketList.add(this);
	}
	
	@Override
	public void run() {
		
	}

}
