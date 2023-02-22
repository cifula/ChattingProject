package com.client.frame;

import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.client.ClientApplication;
import com.client.panel.MainPanel;

import lombok.Getter;

public class MainFrame extends JFrame {
	private static MainFrame instance;
	@Getter
	private static Socket socket;
	
	private static JOptionPane masterExitMessagePanel;
	
	public static MainFrame getInstance() {
		if(instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}
	
	public MainFrame() {
		socket = ClientApplication.getSocket();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 495, 839);
		setContentPane(MainPanel.getInstance());
		
		
	}
	
	public void masterExit() {
		JOptionPane.showMessageDialog(null, "방장이 방을 나갔습니다.", "", JOptionPane.INFORMATION_MESSAGE);
	}
}
