package com.client.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.client.ClientRecive;
import com.client.dto.RequestDto;
import com.client.frame.MainFrame;
import com.google.gson.Gson;

import dto.MakeReqDto;
import lombok.Data;
@Data
public class MenuPanel extends InitPanel {
	public String username = LoginPanel.getInstance().getUsername();
	private static MenuPanel instance;
	private String roomname;
	private DefaultListModel<String> ls = new DefaultListModel<>();
	private Socket socket = MainFrame.getSocket();
	Gson gson = new Gson();
	public static MenuPanel getInstance() {
		if(instance == null) {
			instance = new MenuPanel();
		}
		
		return instance;
	};
	

	private CardLayout mainCard;
	
	public MenuPanel() {
		mainCard = MainPanel.getMainCard();
		ClientRecive clientRecive = new ClientRecive(socket);
		clientRecive.start();
//		로고 이미지
		ImageIcon logoIcon = new ImageIcon("./image/logo.png");
		ImageIcon resizedLogoIcon = new ImageIcon(logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JLabel logoLabel = new JLabel(resizedLogoIcon);
		add(logoLabel);
		logoLabel.setBounds(20, 25, 40, 40);
		
//		플러스 버튼
		ImageIcon plusbuttonIcon = new ImageIcon("./image/plusbutton.png");
		ImageIcon resizedplusbuttonIcon = new ImageIcon(plusbuttonIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		JButton plusButton = new JButton(resizedplusbuttonIcon);
		plusButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { //방만들기 플러스 버튼 누르기
				roomname=JOptionPane.showInputDialog(null, "제목을 적어주세요 : .", "방 생성", JOptionPane.INFORMATION_MESSAGE); // 방제목 입력하기 
				MakeReqDto makeReqDto = new MakeReqDto(username,roomname); //login패널 적은 username과 roomname 저장
				String joinReqDtoJson = gson.toJson(makeReqDto); // requsetDto 에 저장할 body 저장
				RequestDto requestDto = new RequestDto("make", joinReqDtoJson);
				String requestDtoJson = gson.toJson(requestDto); // requestDto 'resourse : make' , 'body :  { roomname : roomname , username : username } (json형태로)' 저장 
				
				OutputStream outputStream;
				try {
					outputStream = socket.getOutputStream();
					PrintWriter out = new PrintWriter(outputStream, true);
					out.println(requestDtoJson); 
					System.out.println(requestDtoJson+ "를 서버로 전송"); // 서버로 전송 되는지 확인용
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				
				}
				
				

			}
		});
		plusButton.setForeground(new Color(255, 255, 255));
		add(plusButton);
		plusButton.setBounds(20, 80, 40, 40);
		plusButton.setBackground(new Color(249, 224, 0));
		plusButton.setBorderPainted(false);

		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		scrollPane.setBounds(80, 0, 400, 800);
		
		JList list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		scrollPane.setViewportView(list);
		
		
		
		ls.addElement("박성진");
		ls.addElement("aaa");
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					String selectedValue = (String) list.getSelectedValue();
					
					System.out.println(selectedValue);
				}
			}
		});
		
		list.setModel(ls);

	


	}
}
