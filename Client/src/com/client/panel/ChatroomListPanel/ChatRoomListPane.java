package com.client.panel.ChatroomListPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.client.entity.RoomPanel;
import com.client.panel.InitPanel;
import com.google.gson.Gson;

public class ChatRoomListPane extends InitPanel {
	private DefaultListModel<JPanel> ChatroomListModel;
	private JList<JPanel> ChatroomList;
	private Gson gson;
	
	public ChatRoomListPane() {
		gson = new Gson();
		JScrollPane ChatroomPane = new JScrollPane();
		ChatroomPane.setBounds(80, 0, 400, 800);
		add(ChatroomPane);
		
//		내채팅방 리스트 모델
		ChatroomListModel = new DefaultListModel<>();
		
		ChatroomList = new JList<>();
		
		ChatroomList.setCellRenderer(new CellRenderer());
		ChatroomList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		ChatroomList.setFont(new Font("D2Coding", Font.BOLD, 17));
		
		ChatroomList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					if(!(ChatroomList.getSelectedValue() == null)) {
						RoomPanel selectedRoomPanel = (RoomPanel) ChatroomList.getSelectedValue();
						int roomId = selectedRoomPanel.getRoom().getRoomId();
						sendRequest("joinRoom", gson.toJson(roomId));
						
					}
				}
			}
		});
		ChatroomList.setModel(ChatroomListModel);
		ChatroomPane.setViewportView(ChatroomList);
	}

	private static class CellRenderer extends DefaultListCellRenderer {
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
