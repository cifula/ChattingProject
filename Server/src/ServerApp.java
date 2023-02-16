

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


class ConnectedSocket extends Thread {
	private static List<ConnectedSocket> socketList = new ArrayList<>(); 
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String username;
	
	public ConnectedSocket(Socket socket) {
		this.socket = socket;
		socketList.add(this);
	}
	
	@Override
	public void run() {
		
		
		
		
		
	}
}
public class ServerApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		
		try {
			System.out.println("서버 실행");
			serverSocket = new ServerSocket(1111);
			
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println(socket.getPort() + "님이 접속되었니다.");
				ConnectedSocket connectedSocket = new ConnectedSocket(socket);
				connectedSocket.start();
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
