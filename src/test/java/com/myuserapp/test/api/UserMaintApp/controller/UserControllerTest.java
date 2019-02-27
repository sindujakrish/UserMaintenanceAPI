package com.myuserapp.test.api.UserMaintApp.controller;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.myuserapp.test.api.UserMaintApp.model.User;
import com.myuserapp.test.api.UserMaintApp.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void registerUserSuccess() throws Exception {
		String name = "Tom Donaldson";
		String email = "Tson@yummy.com";
		
		User mockUser = new User(100, name, email);
		String jsonUser = "{\r\n" + 
				"    \"id\": 100,\r\n" + 
				"    \"name\": \"Tom Donaldson\",\r\n" + 
				"    \"email\": \"Tson@yummy.com\"\r\n" + 
				"}";
		Mockito.when(userService.addUser(name,email)).thenReturn(mockUser);
		RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/registerUser").content(jsonUser).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		MockHttpServletResponse actual = result.getResponse();
		assertEquals(HttpStatus.OK.value(), actual.getStatus());
	}
	
	@Test
	public void registerUserValidationError() throws Exception {
		String name = "";
		String email = "Veron.Smith@youmail.com";
		
		User mockUser = new User(100, name, email);
		String jsonUser = "{\r\n" + 
				"    \"name\": \"\",\r\n" + 
				"    \"email\": \"Veron.Smith@youmail.com\"\r\n" + 
				"}";
		Mockito.when(userService.addUser(name,email)).thenReturn(mockUser);
		RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/registerUser").content(jsonUser).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		String actual = result.getResponse().getContentAsString();
		String expected = "{\"param\":\"text\",\"msg\":\"Must be between 1 and 50 chars long\",\"name\":\"ValidationError\"}";
		assertEquals(expected, actual);
	}
	
	@Test
	public void readUserSuccess() throws Exception {
		User mockUser = new User(100, "Jane Wilson", "JWilson@ymail.com");
		Mockito.when(userService.readUser(100)).thenReturn(mockUser);
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/readUser/100").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		String actual = result.getResponse().getContentAsString();
		String expected = "{\"id\":100,\"name\":\"Jane Wilson\",\"email\":\"JWilson@ymail.com\"}";
		assertEquals(expected, actual);
	}
	
	@Test
	public void readUserNotFound() throws Exception {
		Mockito.when(userService.readUser(102)).thenReturn(null);
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/readUser/102").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		String actual = result.getResponse().getContentAsString();
		String expected = "{\"details\":{\"message\":\"User 102 not found\"},\"name\":\"NotFoundError\"}";
		assertEquals(expected, actual);
	}
	
	@Test
	public void editUserSuccess() throws Exception {
		String name = "Willis Smith";
		String email = "Will.Smith@ynz.com";
		User mockUser = new User(1, name, email);
		String jsonUser = "{\r\n" +  
				"    \"name\": \"Willis Smith\",\r\n" + 
				"    \"email\": \"Will.Smith@ynz.com\"\r\n" + 
				"}";
		Mockito.when(userService.editUser(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString())).thenReturn(mockUser);
		RequestBuilder reqBuilder = MockMvcRequestBuilders.patch("/editUser/1").content(jsonUser).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		String actual = result.getResponse().getContentAsString();
		String expected = "{\"id\":1,\"name\":\"Willis Smith\",\"email\":\"Will.Smith@ynz.com\"}";
		assertEquals(expected, actual);
	}
	
	@Test
	public void editUserNotFound() throws Exception {
		String jsonUser = "{\r\n" +  
				"    \"name\": \"Jane Baker\",\r\n" + 
				"    \"email\": \"JBaker@ops.com\"\r\n" + 
				"}";
		Mockito.when(userService.editUser(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString())).thenReturn(null);
		RequestBuilder reqBuilder = MockMvcRequestBuilders.patch("/editUser/101").content(jsonUser).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		String actual = result.getResponse().getContentAsString();
		String expected = "{\"details\":{\"message\":\"User 101 not found\"},\"name\":\"NotFoundError\"}";
		assertEquals(expected, actual);
	}
	
	@Test
	public void deleteUserSuccess() throws Exception {
		String name = "Willis Smith";
		String email = "Will.Smith@ynz.com";
		User mockUser = new User(1, name, email);
		String jsonUser = "{\r\n" +  
				"    \"name\": \"Willis Smith\",\r\n" + 
				"    \"email\": \"Will.Smith@ynz.com\"\r\n" + 
				"}";
		Mockito.when(userService.deleteUser(Mockito.anyInt())).thenReturn(mockUser);
		RequestBuilder reqBuilder = MockMvcRequestBuilders.delete("/deleteUser/1").content(jsonUser).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		String actual = result.getResponse().getContentAsString();
		String expected = "{\"id\":1,\"name\":\"Willis Smith\",\"email\":\"Will.Smith@ynz.com\"}";
		assertEquals(expected, actual);
	}
	
	@Test
	public void deleteUserNotFound() throws Exception {
		String jsonUser = "{\r\n" +  
				"    \"name\": \"Jane Baker\",\r\n" + 
				"    \"email\": \"JBaker@ops.com\"\r\n" + 
				"}";
		Mockito.when(userService.deleteUser(Mockito.anyInt())).thenReturn(null);
		RequestBuilder reqBuilder = MockMvcRequestBuilders.delete("/deleteUser/101").content(jsonUser).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		String actual = result.getResponse().getContentAsString();
		String expected = "{\"details\":{\"message\":\"User 101 not found\"},\"name\":\"NotFoundError\"}";
		assertEquals(expected, actual);
	}
}
