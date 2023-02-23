package com.client.entity;

import javax.swing.JLabel;
import javax.swing.JPanel;

import lombok.Getter;

public class RoomPanel extends JPanel {
	
	@Getter
	private Room room;
	
    public RoomPanel(Room room) {
    	this.room = room;
        JLabel label = new JLabel(room.getRoomname());
        add(label);
    }
}

