package com.project.grocery.responce;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.grocery.dto.LoginResponceDto;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("serial")
public class LoginResponce implements Serializable {

	private LoginResponceDto user;

	/**
	 * @param user
	 */
	public LoginResponce(LoginResponceDto user) {
		super();
		this.user = user;
	}

	/**
	 * @return the user
	 */
	public LoginResponceDto getUser() {
		return user;
	}
	
	
	
	
}
