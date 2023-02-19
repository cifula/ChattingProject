package com.client.panel;


import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.client.dto.RequestDto;
import com.client.frame.MainFrame;
import com.google.gson.Gson;

public class InitPanel extends JPanel{
	
	private final int WIDTH = 480;
	private final int HEIGHT = 800;
	public final Color kakaoColor = new Color(249, 224, 0);
	public final Color kakaoColor2 = new Color(254, 229, 0);
	private Socket socket;
	private Gson gson;
	
	public InitPanel() {

		setLayout(null);
		setBorder(null);
		setSize(WIDTH, HEIGHT);
		socket = MainFrame.getSocket();
		gson = new Gson();
		
		

	}
	
//	이미지 추가 메소드
	public ImageIcon addImage(String imageName, int width, int length) {
		ImageIcon image = new ImageIcon("./image/" + imageName);
		ImageIcon resizedImage = new ImageIcon(image.getImage().getScaledInstance(width, length, Image.SCALE_SMOOTH));
		return resizedImage;
	}
	
//	요청 보내기 메소드
	public void sendRequest(RequestDto requestDto){
		OutputStream outputStream;
		try {
			outputStream = socket.getOutputStream();
			PrintWriter out = new PrintWriter(outputStream, true);
			out.println(gson.toJson(requestDto));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

}
