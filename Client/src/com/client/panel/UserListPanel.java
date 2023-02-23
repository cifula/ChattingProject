package com.client.panel;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.client.ClientRecive;
import com.client.entity.Room;

import lombok.Getter;
import lombok.Setter;

public class UserListPanel extends InitPanel {

	private static UserListPanel instance;
	
	public static UserListPanel getInstance() {
		if(instance == null) {
			instance = new UserListPanel();
		}
		
		return instance;
	};
	
	@Getter
	private JLabel roomnameLabel;
	@Getter
	private JList<String> userList;
	@Setter
	private Room room;
	
	private UserListPanel() {
		// 로고 이미지
		JLabel logoLabel = new JLabel(addImage("logo.png", 40, 40));
		logoLabel.setBounds(20, 20, 40, 40);
		add(logoLabel);
				
		// 뒤로가기 버튼
		JButton backButton = new JButton(addImage("backbutton.png", 30, 30));
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainPanel.getMainCard().show(MainPanel.getInstance(), "chatroomPanel");
			}
		});
				
		add(backButton);
		backButton.setBounds(410, 20, 40, 40);
		backButton.setBackground(kakaoColor);
				
				
		// 방제목 Label
		roomnameLabel = new JLabel();
		roomnameLabel.setFont(new Font("D2Coding", Font.BOLD, 22));
		roomnameLabel.setBounds(90, 20, 280, 40);
		add(roomnameLabel);
				
		// 채팅방 리스트 패널
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 80, 480, 640);
		add(scrollPane);
				
		userList = new JList<>();
		userList.setFont(new Font("D2Coding", Font.BOLD, 17));
		scrollPane.setViewportView(userList);
		
		// 나가기 버튼
		JButton exitButton = new JButton(addImage("exitbutton.png", 40, 40));
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendRequest("exitRoom", gson.toJson(room.getRoomId()));
				MainPanel.getMainCard().show(MainPanel.getInstance(), "chatroomListPanel");
			}
		});
		exitButton.setBounds(410, 740, 40, 40);
		exitButton.setBackground(kakaoColor);
		add(exitButton);
		
	}

}

