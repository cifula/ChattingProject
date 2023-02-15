package frame;

public class ClientController {
	
	private static ClientController instance;
	private static MainFrame frame;
	
	private ClientController() {}
	
	public static ClientController getInstance() {
		if(instance == null) {
			instance = new ClientController();
		}
		
		return instance;
	}
	
	public void setFrame(MainFrame frame) {
		this.frame = frame;
	}
	

	public void run() {
		frame.setVisible(true);
	}

}
