package com.client;

import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.client.Repository.RoomRepository;
import com.client.dto.ResponseDto;
import com.client.entity.Room;
import com.client.frame.MainFrame;
import com.client.panel.MainPanel;
import com.client.panel.MenuPanel;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientRecive extends Thread{
	
	private final Socket socket;
	private InputStream inputStream;
	private Gson gson;
	private CardLayout mainCard;
	
	@Override
	public void run() {
		try {
			mainCard = MainPanel.getMainCard();
			inputStream = socket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			gson = new Gson();
		
			
			while(true) {
				String response = in.readLine();
				ResponseDto responseDto = gson.fromJson(response, ResponseDto.class);
				switch(responseDto.getResource()) {
					case "getRoomList":
						List<String> rooms = gson.fromJson(responseDto.getBody(), List.class);
						for (String body : rooms) {
							RoomRepository.getInstance().addRoom(gson.fromJson(body, Room.class));
						}
						
						break;
						
					case "join":
						mainCard.show(MainPanel.getInstance(), "menuPanel");
						break;
				
												
				}
			}
			

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
