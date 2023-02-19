package com.client.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.client.dto.RequestDto;

import lombok.Getter;

@Getter
public class MenuPanel extends InitPanel {
	
	
	private static MenuPanel instance;
	
	public static MenuPanel getInstance() {
		if(instance == null) {
			instance = new MenuPanel();
		}
		
		return instance;
	};
	
	
	private CardLayout mainCard;
	private String roomname;
	private DefaultListModel<String> ls;
	private JList roomList;
	
	public MenuPanel() {
		mainCard = MainPanel.getMainCard();
		setBackground(kakaoColor);
		
//		로고 이미지
		ImageIcon logoIcon = new ImageIcon("./image/logo.png");
		ImageIcon resizedLogoIcon = new ImageIcon(logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JLabel logoLabel = new JLabel(resizedLogoIcon);
		add(logoLabel);
		logoLabel.setBounds(20, 25, 40, 40);
		
//		플러스 버튼
		ImageIcon plusbuttonIcon = new ImageIcon("./image/plusbutton.png");
		ImageIcon resizedplusbuttonIcon = new ImageIcon(plusbuttonIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		JButton plusButton = new JButton(resizedplusbuttonIcon);
		plusButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				roomname = JOptionPane.showInputDialog(null, "방이름을 입력해주세요.", "방이름입력", JOptionPane.INFORMATION_MESSAGE);
				RequestDto requestDto = new RequestDto("createRoom", roomname);
				sendRequest(requestDto);
				
				RequestDto requestDto2 = new RequestDto("getRoomList", "pass");
				sendRequest(requestDto2);
			}
		});
		plusButton.setForeground(new Color(255, 255, 255));
		add(plusButton);
		plusButton.setBounds(20, 80, 40, 40);
		plusButton.setBackground(new Color(249, 224, 0));
		plusButton.setBorderPainted(false);

		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		scrollPane.setBounds(80, 0, 400, 800);
		
		roomList = new JList();

		scrollPane.setViewportView(roomList);
		
		ls = new DefaultListModel<>();
		
		roomList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					String selectedValue = (String) roomList.getSelectedValue();
					
					System.out.println(selectedValue);
				}
			}
		});
		
//		roomList.setModel(ls);
		
		

	


	}
}
