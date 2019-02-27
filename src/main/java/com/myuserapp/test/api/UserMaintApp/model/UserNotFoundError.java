package com.myuserapp.test.api.UserMaintApp.model;

import java.util.Map;

public class UserNotFoundError {

	private Map<String, String> details;
	private String name;
	
	public UserNotFoundError() {
		super();
	}

	public UserNotFoundError(Map<String, String> details, String name) {
		super();
		this.details = details;
		this.name = name;
	}

	
	public Map getDetails() {
		return details;
	}

	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
