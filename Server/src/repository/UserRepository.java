package repository;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

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
		gson = new Gson();
		userList = new ArrayList<>();
	}
	
	private Gson gson;
	
	private static List<User> userList;
		
	public void addUser(User user) {
		userList.add(user);
	}
}
