package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class User {
	String username;
	
	public User(String username) {
		this.username = username;
	}
}
