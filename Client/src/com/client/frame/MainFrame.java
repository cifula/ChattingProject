package com.client.frame;

import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import com.client.panel.LoginPanel;
import com.client.panel.MainPanel;

import lombok.Getter;

public class MainFrame extends JFrame {
	private static MainFrame instance;
	@Getter
	private static Socket socket;
	private InputStream inputStream;
	
	public static MainFrame getInstance() {
		if(instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}
	
	public MainFrame() {
		String ip = "127.0.0.1";
		int port = 9090;
		
		try {
			socket = new Socket(ip, port);

			setContentPane(MainPanel.getInstance());
		

			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		
	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 800);
		
		
		
		
		
		
	

	}
}
