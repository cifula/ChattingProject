package com.client.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.client.dto.RequestDto;

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
				
				if(username.isBlank()) {
					JOptionPane.showMessageDialog(null, "아이디는 공란일 수 없습니다.");
				} else if(!username.isBlank()) {
					sendRequest("login", username);					
				}
			}
		});
		
//		엔터키 입력
		loginButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent vK_Enter) {
				username = usernameField.getText();
				
				if(username.isBlank()) {
					JOptionPane.showMessageDialog(null, "아이디는 공란일 수 없습니다.");
				}
				sendRequest("login", username);
			}
		});
		
//		username 입력창
		
		usernameField = new JTextField();
		usernameField.setText("");
		usernameField.setForeground(new Color(0, 0, 0));
		usernameField.setFont(new Font("D2Coding", Font.BOLD, 17));
		usernameField.setBounds(100, 400, 280, 40);
		add(usernameField);

		
	}
}
