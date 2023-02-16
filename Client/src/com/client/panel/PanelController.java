package com.client.panel;

public class PanelController {
	
	private static PanelController instance;
	
	public static PanelController getInstance() {
		if(instance == null) {
			instance = new PanelController();
		}
		
		return instance;
	}
	
	
	private static InitPanel panel;
	
	public void setPanel(InitPanel panel) {
		this.panel = panel;
	}
	
	public InitPanel getPanel() {
		return this.panel;
	}

}
