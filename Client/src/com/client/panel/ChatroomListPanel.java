package com.client.panel;

import java.awt.CardLayout;
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
	private DefaultListModel<JPanel> myRoomListModel;
	private JList<JPanel> roomList;
	private JList<JPanel> myRoomList;
	private InitPanel chatListPane;
	private CardLayout chatListCard;
	
	private ChatroomListPanel() {
		setBackground(kakaoGrayColor);
//		전체 채팅리스트 버튼
		JButton chatroomlistButton = new JButton(addImage("chatroomlist.png", 30, 30));
		chatroomlistButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chatListCard.show(chatListPane, "allChatList");
			}
		});
		chatroomlistButton.setBackground(kakaoGrayColor);
		chatroomlistButton.setBounds(20, 40, 40, 40);
		add(chatroomlistButton);
		
//		내채팅방 리스트 버튼
		JButton myChatroomlistButton = new JButton(addImage("mychatroomlist.png", 30, 30));
		myChatroomlistButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chatListCard.show(chatListPane, "myChatList");
			}
		});
		myChatroomlistButton.setBackground(kakaoGrayColor);
		myChatroomlistButton.setBounds(20, 100, 40, 40);
		add(myChatroomlistButton);
		
//		플러스 버튼
		JButton plusButton = new JButton(addImage("plusbutton.png", 20, 20));
		plusButton.setBackground(kakaoGrayColor);
		plusButton.setBounds(20, 160, 40, 40);
		add(plusButton);
		
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
		
		chatListPane = new InitPanel();
		chatListPane.setBounds(80, 0, 400, 800);
		add(chatListPane);
		chatListCard = new CardLayout();
		chatListPane.setLayout(chatListCard);
		chatListCard.show(chatListPane, "allChatList");
		
		
		

//		전체 채팅방 리스트 패널
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 400, 800);
		chatListPane.add(scrollPane, "allChatList");
		
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
					System.out.println(selectedRoom.toString());
					int roomId = selectedRoom.getRoomId();
					sendRequest("joinRoom", gson.toJson(roomId));
				}
			}
		});
		
		// 내채팅방 리스트 패널
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(0, 0, 400, 800);
		chatListPane.add(scrollPane2, "myChatList");
		
		myRoomListModel = new DefaultListModel<>();
		
		
		myRoomList = new JList<>();
		
		myRoomList.setCellRenderer(new MyCellRenderer());
		myRoomList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		myRoomList.setFont(new Font("D2Coding", Font.BOLD, 17));
		
		scrollPane2.setViewportView(myRoomList);
		
		myRoomList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					RoomPanel selectedRoomPanel = (RoomPanel) roomList.getSelectedValue();
					Room selectedRoom = selectedRoomPanel.getRoom();
					System.out.println(selectedRoom.toString());
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
