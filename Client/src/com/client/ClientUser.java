package com.client;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;


public class ClientUser {
	 private String username;
	 private Socket socket;
	 private String ip = "127.0.0.1";
	 private int port = 1111;
	 private int roomnum = 0;
	 
	    
}	
	// 시작 하기 눌렀을 경우 이름을 가지고 서버에 통신 하기
	 username = 이름입력칸.getText();
	 socket = new Socket(ip, port);
	 JOptionPane.showMessageDialog(null, 
				"접속성공", 
				"카카오톡", 
				JOptionPane.INFORMATION_MESSAGE);
//	 방만들기를 할경우
	
//	roomdto room = new roomdto(roomnum,방이름.getText(),socket)
	 
	
//	}
	
	 
	
