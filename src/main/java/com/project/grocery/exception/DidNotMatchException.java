package com.project.grocery.exception;

import org.hibernate.service.spi.ServiceException;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 15, 2018
 * 
 */
@SuppressWarnings("serial")
public class DidNotMatchException extends ServiceException {

	/**
	 * @param message
	 */
	public DidNotMatchException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
