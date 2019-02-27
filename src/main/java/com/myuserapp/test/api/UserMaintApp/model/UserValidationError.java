package com.myuserapp.test.api.UserMaintApp.model;

public class UserValidationError {
	private String param;
	private String msg;
	private String name;

	public UserValidationError(String param, String msg, String name) {
		super();
		this.param = param;
		this.msg = msg;
		this.name = name;
	}

	public String getParam() {
		return param;
	}

	public String getMsg() {
		return msg;
	}

	public String getName() {
		return name;
	}
}
