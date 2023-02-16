package com.client.panel;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends InitPanel {
	
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
		
		DefaultListModel<String> ls = new DefaultListModel<>();
		
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
