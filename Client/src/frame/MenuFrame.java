package frame;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JTextField;

public class MenuFrame extends MainFrame {
	
	private static MenuFrame instance;
	
	public static MenuFrame getInstance() {
		if(instance == null) {
			instance = new MenuFrame();
		}
		
		return instance;
	};
	

	private JPanel contentPane;
	private JTextField textField;
	
	public MenuFrame() {
		contentPane = new JPanel();
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

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		scrollPane.setBounds(80, 0, 400, 800);
		
		textField = new JTextField();
		scrollPane.setViewportView(textField);
		textField.setColumns(10);
		


	}

}
