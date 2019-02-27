package com.myuserapp.test.api.UserMaintApp.model;

import javax.validation.constraints.Size;

public class UserAddRequest {
	
	@Size(min=1, max = 50)
	private String name;
	
	@Size(min=1, max = 50)
	private String email;
	
	public UserAddRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserAddRequest(String name, String email) {
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
