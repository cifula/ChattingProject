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

import com.client.Repository.RoomRepository;
import com.client.dto.RequestDto;
import com.client.entity.ConnectedUser;
import com.client.entity.Room;
import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ChattingRoomListPanel extends InitPanel {
	
	
	private static ChattingRoomListPanel instance;
	
	public static ChattingRoomListPanel getInstance() {
		if(instance == null) {
			instance = new ChattingRoomListPanel();
		}
		
		return instance;
	};
	
	private String roomname;
	private DefaultListModel<String> ls;
	private JList<String> roomList;
	
	public ChattingRoomListPanel() {
//		로고 이미지
		JLabel logoLabel = new JLabel(addImage("logo.png", 40, 40));
		add(logoLabel);
		logoLabel.setBounds(20, 25, 40, 40);
		
//		플러스 버튼
		JButton plusButton = new JButton(addImage("plusbutton.png", 20, 20));
		plusButton.setForeground(new Color(255, 255, 255));
		add(plusButton);
		plusButton.setBounds(20, 80, 40, 40);
		plusButton.setBackground(kakaoColor);
		
		plusButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				roomname = JOptionPane.showInputDialog(null, "방이름을 입력해주세요.", "방이름입력", JOptionPane.INFORMATION_MESSAGE);
				sendRequest(new RequestDto("createRoom", roomname));
			}
		});

//		채팅방 리스트 패널
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		scrollPane.setBounds(80, 0, 400, 800);
		
		
//		Room 을 다
		ls = new DefaultListModel<>();
		
		
		roomList = new JList<>(ls);
		scrollPane.setViewportView(roomList);
		
		roomList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					int selectedIndex = roomList.getSelectedIndex();
					Room selectedRoom = RoomRepository.getInstance().getRoomList().get(selectedIndex);
					int roomId = selectedRoom.getRoomId();
					RequestDto requestDto = new RequestDto("joinRoom", gson.toJson(roomId));
					sendRequest(requestDto);
				}
			}
		});
	
	}


	

}
