package com.myuserapp.test.api.UserMaintApp.model;

public class UserUpdateRequest {
	
	private String name;
	private String email;
	
	public UserUpdateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserUpdateRequest(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
