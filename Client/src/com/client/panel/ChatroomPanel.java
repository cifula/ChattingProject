package com.client.panel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;

public class ChatroomPanel extends InitPanel {
	
	private static ChatroomPanel instance;
	
	public static ChatroomPanel getInstance() {
		if(instance == null) {
			instance = new ChatroomPanel();
		}
		
		return instance;
	};

	private JPanel contentPane;

	public ChatroomPanel() {
		contentPane = new JPanel();

		
	}

}
