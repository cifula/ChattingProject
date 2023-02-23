package com.client.panel;

import java.awt.CardLayout;

import lombok.Getter;

public class MainPanel extends InitPanel {
	
	private static MainPanel instance;
	
	public static MainPanel getInstance() {
		if(instance == null) {
			instance = new MainPanel();
		}
		
		return instance;
	};

	@Getter
	private static CardLayout mainCard;

	private MainPanel() {
		mainCard = new CardLayout();
		setLayout(mainCard);
		
		add(LoginPanel.getInstance(), "loginPanel");
		add(ChatroomListPanel.getInstance(), "chatroomListPanel");
		add(ChatroomPanel.getInstance(), "chatroomPanel");
		add(UserListPanel.getInstance(), "userListPanel");
		
		mainCard.show(this, "loginPanel");
	}
	

	
}
