package com.client;

import java.awt.EventQueue;

import com.client.frame.MainFrame;

public class ClientApplication {
	
	public static void main(String[] args) {
		Runnable runnable = new Runnable() {
			
			public void run() {
				try {
					
					MainFrame frame = MainFrame.getInstance();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		EventQueue.invokeLater(runnable);
	}

	
}
