package com.project.grocery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.grocery.request.PasswordEditRequest;
import com.project.grocery.request.UserCreationRequest;
import com.project.grocery.request.UserEditRequest;
import com.project.grocery.service.UserService;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@RestController
@RequestMapping(value="/api/v1/user")
public class UserController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Object> saveUser(@RequestHeader Long userId ,@Validated @RequestBody UserCreationRequest userDto) {
		LOG.debug("Request for user creation in accepted");
		userService.saveUser(userId,userDto);
		LOG.debug("user is created");
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	

	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deletuser(@PathVariable("id") Long id){
		LOG.debug("Request accepted to delete user.");
		userService.deleteUser(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Object> editUser(@RequestBody UserEditRequest userEditRequest){
		LOG.debug("Request accepted to edit user.");
		userService.editUser(userEditRequest);
		
		return new ResponseEntity<Object>(HttpStatus.OK);
		
		
		
	}
	
	@RequestMapping(value="/changePassword",method=RequestMethod.PUT)
	public ResponseEntity<Object> changePassword(@RequestHeader Long userId,
			@RequestBody PasswordEditRequest passwordEditRequest){
		LOG.debug("Request accepted to change password.");
		userService.changePassword(userId,passwordEditRequest);
		return new ResponseEntity<Object>(HttpStatus.OK);
		
	}
	
}	


