package com.client.frame;

import javax.swing.JFrame;

import com.client.panel.LoginPanel;
import com.client.panel.MenuPanel;

public class MainFrame extends JFrame {

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 800);
		
		
		LoginPanel loginPanel = LoginPanel.getInstance();
		MenuPanel menuPanel = MenuPanel.getInstance();
		

		setContentPane(menuPanel);			
	

	}
}
