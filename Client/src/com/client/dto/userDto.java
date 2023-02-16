package dto;

import java.net.Socket;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class userDto { //유저 
	private String username;
	private Socket connectedsocket;
}
