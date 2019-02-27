package com.myuserapp.test.api.UserMaintApp.controller;


import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myuserapp.test.api.UserMaintApp.exception.CustomException;
import com.myuserapp.test.api.UserMaintApp.exception.ObjectNotFoundException;
import com.myuserapp.test.api.UserMaintApp.model.User;
import com.myuserapp.test.api.UserMaintApp.model.UserAddRequest;
import com.myuserapp.test.api.UserMaintApp.model.UserNotFoundError;
import com.myuserapp.test.api.UserMaintApp.model.UserUpdateRequest;
import com.myuserapp.test.api.UserMaintApp.model.UserValidationError;
import com.myuserapp.test.api.UserMaintApp.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Controller
@Api(value="APIForUserMaint", tags = "user", description = " User endpoints" ,produces=MediaType.APPLICATION_JSON_VALUE)
public class UserController {
		
	@Autowired
	UserService userService;
	
	@Autowired
    private JavaMailSender sender;
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation("Registers a User")
	@ApiResponses(value= {@ApiResponse(code=200, message = "OK", response = User.class),
			@ApiResponse(code=400,message="Validation Error", response = UserValidationError.class)})
	public User registerUser(@RequestBody UserAddRequest body, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			throw new CustomException("");
		}
		String name = body.getName();
		String email = body.getEmail();
		if(name ==null || name.length()<1 || name.length()>50) {
			throw new CustomException("");
		}
		User userObj = userService.addUser(name, email);
		if(userObj!= null) {
			sendEmail(userObj.getEmail(), userObj.getName());
		}
		return userObj;
	}
	
	@RequestMapping(value = "/readUser/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation("Retrieve a specific user by id")
	@ApiResponses(value= {@ApiResponse(code=200, message = "OK", response = User.class),
			@ApiResponse(code=400,message="Validation Error", response = UserValidationError.class),
			@ApiResponse(code=404,message="Not Found Error", response = UserNotFoundError.class)})
	public User readUser(@PathVariable int id) {
		User user = userService.readUser(id);
		if(user == null) {
			throw new ObjectNotFoundException(String.valueOf(id));
		}
		return user;
	}
	
	@RequestMapping(value = "/editUser/{id}", method = RequestMethod.PATCH)
	@ResponseBody
	@ApiOperation("Modify a user")
	@ApiResponses(value= {@ApiResponse(code=200, message = "OK", response = User.class),
			@ApiResponse(code=400,message="Validation Error", response = UserValidationError.class),
			@ApiResponse(code=404,message="Not Found Error", response = UserNotFoundError.class)})
	public User editUser(@RequestBody UserUpdateRequest body, BindingResult bindingResult, @PathVariable int id) {
		if (bindingResult.hasErrors()) {
			throw new CustomException("");
		}
		String name = body.getName();
		String email = body.getEmail();
		
		if(name ==null || name.length()<1 || name.length()>50) {
			throw new CustomException("");
		}
		User userObj = userService.editUser(name, id, email);
		if(userObj==null) {
			throw new ObjectNotFoundException(String.valueOf(id));
		}
		return userObj;
	}
	
	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ApiOperation("Delete a specific user identified by id")
	@ApiResponses(value= {@ApiResponse(code=200, message = "OK", response = User.class),
			@ApiResponse(code=400,message="Validation Error", response = UserValidationError.class),
			@ApiResponse(code=404,message="Not Found Error", response = UserNotFoundError.class)})
	public User deleteUser(@PathVariable int id) {
		User user = userService.deleteUser(id);
		if(user == null) {
			throw new ObjectNotFoundException(String.valueOf(id));
		}
		return user;
	}
	
	private void sendEmail(String email, String name) throws Exception{
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(email);
		helper.setText("Hello "+name+" Welcome!! Your user details have been registered successfully!!!");
		helper.setSubject("User registration Success");
		sender.send(message);		    
	}

	
}
