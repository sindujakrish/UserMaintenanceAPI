package com.myuserapp.test.api.UserMaintApp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.myuserapp.test.api.UserMaintApp.model.User;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserService.class, secure = false)
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	
	@Test
	public void addUserSuccess() throws Exception {
		User actualUser = userService.addUser("Sidney Cooper","SCoop@bbt.com");
		assertNotEquals(null, actualUser);
	}
		
	@Test
	public void readUserSuccess() throws Exception {
		User user = new User(2, "Donald Duck", "DtheDuck@myemail.com");
		User actualUser = userService.readUser(2);
		assertEquals(user.getName(), actualUser.getName());
		assertEquals(user.getEmail(), actualUser.getEmail());
	}
	
	@Test
	public void readUserNotFound() throws Exception {
		User actualUser = userService.readUser(130);
		assertEquals(null, actualUser);
	}
	
	@Test
	public void editUserSuccess() throws Exception {
		String name = "Remola Joseph";
		String email = "RJ@gmail.com";
		User mockUser = new User(1, name, email);
		User actualUser = userService.editUser(name,1,email);
		assertEquals(mockUser.getName(), actualUser.getName());
		assertEquals(mockUser.getEmail(), actualUser.getEmail());
	}
	
	@Test
	public void editUserNotFound() throws Exception {
		User actualUser = userService.editUser("Sample User", 109, "semail@email.com");
		assertEquals(null, actualUser);
	}
	
	@Test
	public void deleteUserSuccess() throws Exception {
		String name = "Scooby Doo";
		User mockUser = new User(3, name, "TestMail");
		User actualUser = userService.deleteUser(3);
		assertEquals(mockUser.getName(), actualUser.getName());
	}
	
	@Test
	public void deleteUserNotFound() throws Exception {
		User actualUser = userService.deleteUser(109);
		assertEquals(null, actualUser);
	}
}
