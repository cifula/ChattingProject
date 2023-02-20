package com.client.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.client.frame.MainFrame;

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
		add(ChattingRoomListPanel.getInstance(), "menuPanel");
		add(ChatroomPanel.getInstance(), "chatroomPanel");
		
		mainCard.show(this, "loginPanel");
	}
	

	
}
