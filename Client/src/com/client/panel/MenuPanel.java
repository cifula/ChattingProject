package com.client.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.client.dto.MakeReqDto;
import com.client.dto.RequestDto;
import com.client.frame.MainFrame;
import com.google.gson.Gson;

public class MenuPanel extends InitPanel {
	private String roomname;
	
	Gson gson = new Gson(); 
	private static MenuPanel instance;
	
	public static MenuPanel getInstance() {
		if(instance == null) {
			instance = new MenuPanel();
		}
		
		return instance;
	};
	

	private CardLayout mainCard;
	private JPanel contentPane;
	
	public MenuPanel() {
		DefaultListModel<String> ls = new DefaultListModel<>();
		mainCard = new CardLayout();
		setLayout(mainCard);
		
		contentPane = new JPanel();
		add(contentPane, "contentPane");
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(249, 224, 0));
		
//		로고 이미지
		ImageIcon logoIcon = new ImageIcon("./image/logo.png");
		ImageIcon resizedLogoIcon = new ImageIcon(logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JLabel logoLabel = new JLabel(resizedLogoIcon);
		contentPane.add(logoLabel);
		logoLabel.setBounds(20, 25, 40, 40);
		
//		플러스 버튼
		ImageIcon plusbuttonIcon = new ImageIcon("./image/plusbutton.png");
		ImageIcon resizedplusbuttonIcon = new ImageIcon(plusbuttonIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		JButton plusButton = new JButton(resizedplusbuttonIcon);
		plusButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { //방만들기 플러스 버튼 누르기
				roomname=JOptionPane.showInputDialog(null, "제목을 적어주세요 : .", "방 생성", JOptionPane.INFORMATION_MESSAGE);
				ls.addElement(roomname);
				MakeReqDto joinReqDto = new MakeReqDto(LoginPanel.getInstance().getUsername(),roomname);
				String joinReqDtoJson = gson.toJson(joinReqDto);
				RequestDto requestDto = new RequestDto("make", joinReqDtoJson);
				String requestDtoJson = gson.toJson(requestDto);
			
				OutputStream outputStream;
				try {
					outputStream = LoginPanel.getInstance().getSocket().getOutputStream();
					PrintWriter out = new PrintWriter(outputStream, true);
					out.println(requestDtoJson);
					System.out.println(requestDtoJson+ "를 서버로 전송");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		plusButton.setForeground(new Color(255, 255, 255));
		contentPane.add(plusButton);
		plusButton.setBounds(20, 80, 40, 40);
		plusButton.setBackground(new Color(249, 224, 0));
		plusButton.setBorderPainted(false);

		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
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
