package com.client;

import com.client.frame.MainFrame;

public class RunMainFrame extends Thread{
	@Override
	public void run() {
		MainFrame frame = MainFrame.getInstance();
		frame.setVisible(true);
	}

}
