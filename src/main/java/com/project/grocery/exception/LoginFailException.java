package com.project.grocery.exception;

import org.hibernate.service.spi.ServiceException;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@SuppressWarnings("serial")
public class LoginFailException extends ServiceException {

	/**
	 * @param message
	 */
	public LoginFailException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
