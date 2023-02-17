package com.client.panel;


import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
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
	
	public ImageIcon addImage(String imageName, int width, int length) {
		ImageIcon image = new ImageIcon("./image/" + imageName);
		ImageIcon resizedImage = new ImageIcon(image.getImage().getScaledInstance(width, length, Image.SCALE_SMOOTH));
		return resizedImage;
	}
	

}
