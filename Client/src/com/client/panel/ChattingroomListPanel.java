package com.client.panel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.client.Repository.RoomRepository;
import com.client.dto.RequestDto;
import com.client.entity.Room;

import lombok.Getter;

@Getter
public class ChattingroomListPanel extends InitPanel {
	
	
	private static ChattingroomListPanel instance;
	
	public static ChattingroomListPanel getInstance() {
		if(instance == null) {
			instance = new ChattingroomListPanel();
		}
		
		return instance;
	};
	
	private String roomname;
	private DefaultListModel<String> roomListModel;
	private JList<String> roomList;
	
	public ChattingroomListPanel() {
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
				sendRequest("createRoom", roomname);
			}
		});

//		채팅방 리스트 패널
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		scrollPane.setBounds(80, 0, 400, 800);
		
		roomListModel = new DefaultListModel<>();
		
		
		roomList = new JList<>(roomListModel);
		scrollPane.setViewportView(roomList);
		
		roomList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					int selectedIndex = roomList.getSelectedIndex();
					Room selectedRoom = RoomRepository.getInstance().getRoomList().get(selectedIndex);
					int roomId = selectedRoom.getRoomId();
					sendRequest("joinRoom", gson.toJson(roomId));
				}
			}
		});
	
	}
	


	

}
