import frame.ClientController;
import frame.LoginFrame;

public class ChattingClient {
	
	public static void main(String[] args) {
		ClientController.getInstance().setFrame(LoginFrame.getInstance());
		
		while(true) {
			ClientController.getInstance().run();
			
		}
	}

}
