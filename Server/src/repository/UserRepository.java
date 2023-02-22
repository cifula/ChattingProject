package repository;

import java.util.ArrayList;
import java.util.List;

import entity.User;
import lombok.Data;

@Data
public class UserRepository {
	
	private static UserRepository instance;
	
	public static UserRepository getInstance() {
		if(instance == null) {
			instance = new UserRepository();
		}
		return instance;
	}
	
	private UserRepository() {
		userList = new ArrayList<>();
	}
	
	private List<User> userList;
		
	public void addUser(User user) {
		userList.add(user);
	}
	
	public User findUserByUserId(int userId) {
		for(User user: userList) {
			if(user.getUserId() == userId) {
				return user;
			}
		}
		return null;
	}
}
