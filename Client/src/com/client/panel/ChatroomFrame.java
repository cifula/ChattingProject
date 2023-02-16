package com.client.panel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;

public class ChatroomFrame extends InitPanel {
	
	private static ChatroomFrame instance;
	
	public static ChatroomFrame getInstance() {
		if(instance == null) {
			instance = new ChatroomFrame();
		}
		
		return instance;
	};

	private JPanel contentPane;

	public ChatroomFrame() {
		contentPane = new JPanel();

		setCont entPane(contentPane);
		
	}

}
