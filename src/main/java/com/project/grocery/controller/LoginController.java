package com.project.grocery.controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.grocery.dto.LoginDto;
import com.project.grocery.dto.LoginResponceDto;
import com.project.grocery.request.ForgetPasswordRequest;
import com.project.grocery.responce.LoginResponce;
import com.project.grocery.service.LoginService;
import com.project.grocery.util.Status;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@RestController
@RequestMapping(value = "/api/v1")
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto) {
		LOG.debug("Login Request", loginDto.getUsername());
		LoginResponceDto loginResponceDto = loginService.logInUser(loginDto.getUsername(), loginDto.getPassword(),
				Status.ACTIVE, loginDto.getDeviceId());
		return new ResponseEntity<Object>(new LoginResponce(loginResponceDto), HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Object> logout(@RequestHeader Long userId) {
//		HttpServletRequest request = null;
//		@SuppressWarnings("null")
//		HttpSession httpSession=request.getSession(false);
//		httpSession.invalidate();
		loginService.logout(userId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public ResponseEntity<Object> forgetPassword(@RequestBody String Email){
		LOG.debug("Request Accepted for Reset password");
		loginService.resetPassword(Email);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/resetPassword/{value}",method=RequestMethod.POST)
	public ResponseEntity<Object> resetPassword(@PathVariable ("value") String token,@RequestBody ForgetPasswordRequest forgetPasswordRequest){
		LOG.debug("Request Accepted to reset password");
		loginService.resetForgetPassword(token,forgetPasswordRequest);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
}
