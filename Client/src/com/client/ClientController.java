package com.client;

import com.client.frame.LoginFrame;
import com.client.frame.MainFrame;

public class ClientController {
	
	private static ClientController instance;
	private static MainFrame frame;
	
	private ClientController() {
		frame = LoginFrame.getInstance();
	}
	
	public static ClientController getInstance() {
		if(instance == null) {
			instance = new ClientController();
		}
		
		return instance;
	}
	
	public void setFrame(MainFrame frame) {
		this.frame = frame;
	}
	
	public static void run() {
		frame.setVisible(true);
	}
}
