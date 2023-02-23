package com.client.panel;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.client.dto.SendMessageDto;
import com.client.entity.ConnectedUser;
import com.client.entity.Room;

import lombok.Getter;
import lombok.Setter;

public class ChatroomPanel extends InitPanel {
	
	private static ChatroomPanel instance;
	
	public static ChatroomPanel getInstance() {
		if(instance == null) {
			instance = new ChatroomPanel();
		}
		
		return instance;
	};
	
	@Setter
	@Getter
	private JTextArea contentArea;
	
	@Getter
	private JLabel roomnameLabel;
	@Setter
	private Room room;

	private ChatroomPanel() {		
		setBackground(kakaoColor);
		
		room = new Room();
		
		// 로고 이미지
		JLabel logoLabel = new JLabel(addImage("logo.png", 40, 40));
		logoLabel.setBounds(20, 20, 40, 40);
		add(logoLabel);
		
		// 리스트 버튼
		JButton listButton = new JButton(addImage("listbutton.png", 30, 30));
		listButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainPanel.getMainCard().show(MainPanel.getInstance(), "userListPanel");
			}
		});
		
		add(listButton);
		listButton.setBounds(410, 20, 40, 40);
		listButton.setBackground(kakaoColor);
		
		
		// 방제목 Label
		roomnameLabel = new JLabel();
		roomnameLabel.setFont(new Font("D2Coding", Font.BOLD, 22));
		roomnameLabel.setBounds(90, 20, 280, 40);
		add(roomnameLabel);
		
		// 메세지 내용 패널
		JScrollPane chatContentPanel = new JScrollPane();
		chatContentPanel.setBounds(0, 80, 480, 600);
		add(chatContentPanel);
		
		// 메세지 내용 Area
		contentArea = new JTextArea();
		contentArea.setFont(new Font("D2Coding", Font.PLAIN, 17));
		chatContentPanel.setViewportView(contentArea);
		
		// 메세지 보내기 패널
		JScrollPane messagePanel = new JScrollPane();
		messagePanel.setBounds(0, 680, 390, 120);
		add(messagePanel);
		
		JTextArea messageInput = new JTextArea();
		messagePanel.setViewportView(messageInput);
		
		// 메세지 보내기 아이콘
		JButton sendButton = new JButton(addImage("send.png", 40, 40));
		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SendMessageDto sendMessageDto = new SendMessageDto(ConnectedUser.getInstance().getUser(), room.getRoomId(), messageInput.getText());
				sendRequest("sendMessage", gson.toJson(sendMessageDto));
				messageInput.setText("");
			}
		});
		sendButton.setBounds(390, 680, 90, 120);
		sendButton.setBackground(new Color(255, 255, 255));
		add(sendButton);
		
		// 엔터키 입력
		sendButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent VK_ENTER) {
				SendMessageDto sendMessageDto = new SendMessageDto(ConnectedUser.getInstance().getUser(), room.getRoomId(), messageInput.getText());
				sendRequest("sendMessage", gson.toJson(sendMessageDto));
				messageInput.setText("");
			}
		});
		
		
		
		
	}
}
