package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import com.client.frame.MainFrame;
import com.client.panel.MenuPanel;
import com.google.gson.Gson;

import dto.MakeRespDto;
import dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class ClientRecive extends Thread {

	private final Socket socket = MainFrame.getSocket();
	private InputStream inputStream;
	private Gson gson;
	
	@Override
	public void run() {
		try {
			inputStream = socket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			gson = new Gson();
			
			while(true) {
				String request = in.readLine();
				ResponseDto responseDto = gson.fromJson(request, ResponseDto.class);
				switch(responseDto.getResource()) {
					case "make":
							MakeRespDto makeRespDto = gson.fromJson(responseDto.getBody(), MakeRespDto.class);
							MenuPanel.getInstance().getLs().addElement(makeRespDto.getRoomname());
							System.out.println(makeRespDto.getRoomname());
						break;
						
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}














