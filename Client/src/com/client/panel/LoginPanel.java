package com.client.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.client.ClientRecive;
import com.client.dto.RequestDto;
import com.client.frame.MainFrame;
import com.google.gson.Gson;

import dto.LoginReqDto;
import dto.MakeReqDto;
import lombok.Data;
@Data
public class LoginPanel extends InitPanel {
	private OutputStream outputStream;
	private static LoginPanel instance;
	public static LoginPanel getInstance() {
		if(instance == null) {
			instance = new LoginPanel();
		}
		
		return instance;
	};

	private CardLayout mainCard;
	private JTextField usernameField;
	private String username;
	
	private Socket socket = MainFrame.getSocket();
	private Gson gson;
	

	private LoginPanel() {
		gson = new Gson();
		
		setBackground(kakaoColor);
		mainCard = MainPanel.getMainCard();
//		로고 이미지
		ImageIcon logoIcon = new ImageIcon("./image/logo.png");
		ImageIcon resizedLogoIcon = new ImageIcon(logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
		JLabel logoLabel = new JLabel(resizedLogoIcon);
		add(logoLabel);
		logoLabel.setBounds(200, 200, 80, 80);
		
//		시작하기 버튼 이미지
		ImageIcon loginbuttonIcon = new ImageIcon("./image/loginbutton.png");
		ImageIcon resizedLoginbuttonIcon = new ImageIcon(loginbuttonIcon.getImage().getScaledInstance(280, 40, Image.SCALE_SMOOTH));
		
		
//		로그인 버튼
		
		JButton loginButton = new JButton(resizedLoginbuttonIcon);
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				username = usernameField.getText();
				LoginReqDto loginReqDto = new LoginReqDto(username); 
				String loginReqDtoJson = gson.toJson(loginReqDto); 
				RequestDto requestDto = new RequestDto("login", loginReqDtoJson);
				String requestDtoJson = gson.toJson(requestDto);  

				try {
					outputStream = socket.getOutputStream();
					PrintWriter out = new PrintWriter(outputStream, true);
					out.println(requestDtoJson); 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				
				}
					
					
				mainCard.show(MainPanel.getInstance(), "menuPanel");
					

				
			}
		});
		loginButton.setBackground(new Color(254, 229, 0));
		loginButton.setBounds(100, 440, 280, 40);
		add(loginButton);
		
//		--
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("D2Coding", Font.BOLD, 17));
		usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameField.setBounds(100, 380, 280, 45);
		add(usernameField);
		usernameField.setColumns(10);

	}
	

	
}
