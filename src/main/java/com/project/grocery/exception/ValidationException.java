package com.project.grocery.exception;

import org.hibernate.service.spi.ServiceException;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 6, 2018
 * 
 */
@SuppressWarnings("serial")
public class ValidationException extends ServiceException {

	/**
	 * @param message
	 */
	public ValidationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
