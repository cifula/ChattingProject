package com.client.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.client.dto.RequestDto;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginPanel extends InitPanel {
	
	private static LoginPanel instance;
	
	public static LoginPanel getInstance() {
		if(instance == null) {
			instance = new LoginPanel();
		}
		
		return instance;
	};

	private JTextField usernameField;
	private static String username;

	private LoginPanel() {	
//		로고
		JLabel logoLabel = new JLabel(addImage("logo.png", 80, 80));
		add(logoLabel);
		logoLabel.setBounds(200, 200, 80, 80);
	
//		로그인 버튼
		
		JButton loginButton = new JButton(addImage("loginbutton.png", 280, 40));
		loginButton.setBounds(100, 460, 280, 40);
		add(loginButton);
		
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {			
				username = usernameField.getText();
				sendRequest(new RequestDto("getRoomList", "pass"));
				sendRequest(new RequestDto("login", username));
			}
		});

		
//		username 입력창
		
		usernameField = new JTextField();
		
		usernameField.setForeground(new Color(192, 192, 192));
		usernameField.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 13));
		usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameField.setText("Insert ID");
		usernameField.setBounds(100, 400, 280, 40);
		add(usernameField);
		
//		username 입력창 클릭 이벤트
		usernameField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(usernameField.getText().equals("Insert ID")) {
					usernameField.setText("");
					usernameField.setForeground(new Color(0, 0, 0));
					usernameField.setFont(new Font("D2Coding", Font.BOLD, 17));
				}
			}
		});

	}
}
