package com.myuserapp.test.api.UserMaintApp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myuserapp.test.api.UserMaintApp.model.User;


@Service
public class UserService {
	
	private static List<User> userList = new ArrayList<User>();
	private static int count =4;
	
	static {
		userList.add(new User(1, "John Doe", "John.Doe@mymail.com"));
		userList.add(new User(2, "Donald Duck", "DtheDuck@myemail.com"));
		userList.add(new User(3, "Scooby Doo","SDoo@yeehaw.com"));
	}

	public User readUser(int id) {
		User user = null;
		for(User userObj: userList) {
			if(userObj.getId() == id) {
				user = userObj;
				break;
			}
		}
		return user;
	}
	
	public User addUser(String name, String email) {
		User user = new User(count++, name, email);
		userList.add(user);
		return user;
	}
	
	public User editUser(String name, int id, String email) {
		for(User userObj: userList) {
			if(userObj.getId()== id) {
				userObj.setName(name);
				userObj.setEmail(email);
				return userObj;
			}
		}
		return null;
	}
	
	public User deleteUser(int id) {
		User user = null;
		for(User userObj: userList) {
			if(userObj.getId() == id) {
				user = userObj;
				userList.remove(userObj);
				break;
			}
		}
		return user;
	}
}

