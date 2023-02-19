package entity;

import lombok.Data;

@Data
public class User {
	private static int ai = 0;
	private int userId = 0;
	private String username;
	
	public User(String username) {
		this.username = username;
		userId = ai;
		ai++;
	}
}
