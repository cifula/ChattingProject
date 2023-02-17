package com.client.panel;


import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InitPanel extends JPanel{
	
	private final int WIDTH = 480;
	private final int HEIGHT = 800;
	public final Color kakaoColor = new Color(249, 224, 0);
	public final Color kakaoColor2 = new Color(254, 229, 0);
	
	public InitPanel() {

		setLayout(null);
		setBorder(null);
		setSize(WIDTH, HEIGHT);
	}

}
