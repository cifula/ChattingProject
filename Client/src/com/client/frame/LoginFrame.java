package com.client.frame;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.CompoundBorder;

import com.client.ClientController;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends MainFrame {
	
	private static LoginFrame instance;
	
	public static LoginFrame getInstance() {
		if(instance == null) {
			instance = new LoginFrame();
		}
		
		return instance;
	};

	private JPanel contentPane;
	private JTextField usernameField;
	private ClientController controller;

	private LoginFrame() {
		contentPane = new JPanel();
		contentPane.setBackground(new Color(249, 224, 0));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		controller = ClientController.getInstance();
		

//		로고 이미지
		ImageIcon logoIcon = new ImageIcon("./image/logo.png");
		ImageIcon resizedLogoIcon = new ImageIcon(logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
		JLabel logoLabel = new JLabel(resizedLogoIcon);
		contentPane.add(logoLabel);
		logoLabel.setBounds(200, 200, 80, 80);
		
//		시작하기 버튼 이미지
		ImageIcon loginbuttonIcon = new ImageIcon("./image/loginbutton.png");
		ImageIcon resizedLoginbuttonIcon = new ImageIcon(loginbuttonIcon.getImage().getScaledInstance(280, 40, Image.SCALE_SMOOTH));
		
		
//		로그인 버튼
		
		JButton loginButton = new JButton(resizedLoginbuttonIcon);
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.setFrame(MenuFrame.getInstance());
			}
		});
		loginButton.setBackground(new Color(254, 229, 0));
		loginButton.setBounds(100, 440, 280, 40);
		contentPane.add(loginButton);
		
//		--
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("D2Coding", Font.BOLD, 17));
		usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameField.setBounds(100, 380, 280, 45);
		contentPane.add(usernameField);
		usernameField.setColumns(10);

	}
}
