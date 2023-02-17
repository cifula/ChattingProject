package com.client.panel;


import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatroomPanel extends InitPanel {
	
	private static ChatroomPanel instance;
	
	public static ChatroomPanel getInstance() {
		if(instance == null) {
			instance = new ChatroomPanel();
		}
		
		return instance;
	};


	public ChatroomPanel() {
		setBackground(kakaoColor);
		
		// 로고 이미지
		JLabel logoLabel = new JLabel(addImage("logo.png", 40, 40));
		add(logoLabel);
		logoLabel.setBounds(20, 20, 40, 40);
		
		// 나가기 이미지
		JButton exitButton = new JButton(addImage("exitbutton.png", 30, 30));
		add(exitButton);
		exitButton.setBounds(410, 20, 40, 40);
		exitButton.setBackground(kakaoColor);
		
		
		// 채팅 패널
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 80, 480, 720);
		add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		
		
	}
}
