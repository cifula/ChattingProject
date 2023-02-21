package com.client;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import lombok.Getter;

public class ClientApplication {
	@Getter
	private static Socket socket;

	public static void main(String[] args) {
		Runnable runnable = new Runnable() {
			
			public void run() {
				try {
					String ip = "127.0.0.1";
					int port = 9090;
					try {
						socket = new Socket(ip, port);
						
						
					} catch (UnknownHostException e) {
						e.printStackTrace();
						
					} catch (IOException e) {
						e.printStackTrace();
						
					} 
					
					RunMainFrame runMainFrame = new RunMainFrame();
					runMainFrame.start();
					
					ClientRecive clientRecive = new ClientRecive(socket);
					clientRecive.start();
					

					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		EventQueue.invokeLater(runnable);
	}

	
}
