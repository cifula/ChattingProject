package com.client.frame;

import java.io.InputStream;
import java.net.Socket;

import javax.swing.JFrame;

import com.client.ClientApplication;
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
		socket = ClientApplication.getSocket();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 800);
		setContentPane(MainPanel.getInstance());


	}
}
