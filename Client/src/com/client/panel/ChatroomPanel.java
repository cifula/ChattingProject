package com.client.panel;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatroomPanel extends InitPanel {
	
	private static ChatroomPanel instance;
	
	public static ChatroomPanel getInstance() {
		if(instance == null) {
			instance = new ChatroomPanel();
		}
		
		return instance;
	};
	
	private CardLayout mainCard;


	public ChatroomPanel() {
		mainCard = MainPanel.getMainCard();
		
		setBackground(kakaoColor);
		
		// 로고 이미지
		JLabel logoLabel = new JLabel(addImage("logo.png", 40, 40));
		add(logoLabel);
		logoLabel.setBounds(20, 20, 40, 40);
		
		// 나가기 이미지
		JButton exitButton = new JButton(addImage("exitbutton.png", 30, 30));
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainCard.show(MainPanel.getInstance(), "menuPanel");
			}
		});
		add(exitButton);
		exitButton.setBounds(410, 20, 40, 40);
		exitButton.setBackground(kakaoColor);
		
		
		// 채팅 내용 패널
		JScrollPane chatContentPanel = new JScrollPane();
		chatContentPanel.setBounds(0, 80, 480, 600);
		add(chatContentPanel);
		
		JTextArea textArea = new JTextArea();
		chatContentPanel.setViewportView(textArea);
		
		// 메세지 보내기 패널
		
		JScrollPane messagePanel = new JScrollPane();
		messagePanel.setBounds(0, 680, 390, 120);
		add(messagePanel);
		
		// 메세지 보내기 아이콘
		JButton sendButton = new JButton(addImage("send.png", 40, 40));
		sendButton.setBounds(390, 680, 90, 120);
		sendButton.setBackground(new Color(255, 255, 255));
		add(sendButton);
		
		
	}
}
