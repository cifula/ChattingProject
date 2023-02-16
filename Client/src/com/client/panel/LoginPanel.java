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

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class LoginPanel extends InitPanel {
	
	private static LoginPanel instance;
	private String username;
	private Socket socket;
	private String ip = "127.0.0.1";
	private int port = 1111;
	private int roomnum = 0;
	
	public static LoginPanel getInstance() {
		if(instance == null) {
			instance = new LoginPanel();
		}
		
		return instance;
	};
	
	public void Init() {
		MenuPanel menuPanel = MenuPanel.getInstance();
		add(menuPanel, "menuPanel");
	}

	private CardLayout mainCard;
	private JPanel contentPane;
	private JTextField usernameField;
	
	
	private LoginPanel() {
		mainCard = new CardLayout();
		setLayout(mainCard);
		
		contentPane = new JPanel();
		add(contentPane, "contentPane");
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(249, 224, 0));
	


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
//				mainCard.show(menuPanel, "menuPanel");
				username = usernameField.getText();
				JOptionPane.showMessageDialog(null, 
							"접속성공", 
							"카카오톡", 
							JOptionPane.INFORMATION_MESSAGE);
				 try {
					socket = new Socket(ip, port);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
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
