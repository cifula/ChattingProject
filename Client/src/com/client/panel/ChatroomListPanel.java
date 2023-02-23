package com.client.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.client.Repository.RoomRepository;
import com.client.entity.Room;
import com.client.entity.RoomPanel;

import lombok.Getter;

@Getter
public class ChatroomListPanel extends InitPanel {
	
	
	private static ChatroomListPanel instance;
	
	public static ChatroomListPanel getInstance() {
		if(instance == null) {
			instance = new ChatroomListPanel();
		}
		
		return instance;
	};
	
	private String roomname;
	private DefaultListModel<JPanel> roomListModel;
	private JList<JPanel> roomList;
	
	private ChatroomListPanel() {
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
				if(roomname == null) {
					
				} else {
					if(roomname.isBlank()) {
						JOptionPane.showMessageDialog(null, "방이름은 공란일 수 없습니다.");
					} else if (!roomname.isBlank()) {
						sendRequest("createRoom", roomname);					
					} 
				}
			}
		});

//		채팅방 리스트 패널
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(80, 0, 400, 800);
		add(scrollPane);
		
		roomListModel = new DefaultListModel<>();
		
		
		roomList = new JList<>();
		
		roomList.setCellRenderer(new MyCellRenderer());
		roomList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		roomList.setFont(new Font("D2Coding", Font.BOLD, 17));
		
		scrollPane.setViewportView(roomList);
		
		roomList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					RoomPanel selectedRoomPanel = (RoomPanel) roomList.getSelectedValue();
					Room selectedRoom = selectedRoomPanel.getRoom();
					int roomId = selectedRoom.getRoomId();
					sendRequest("joinRoom", gson.toJson(roomId));
				}
			}
		});
	}
	
	private static class MyCellRenderer extends DefaultListCellRenderer {
	    @Override
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        RoomPanel roomPanel = (RoomPanel) value;
	        if (isSelected) {
	            roomPanel.setBackground(new Color(242, 242, 242));
	        } else {
	            roomPanel.setBackground(list.getBackground());
	        }
	        return roomPanel;
	    }
	}
	


	

}
