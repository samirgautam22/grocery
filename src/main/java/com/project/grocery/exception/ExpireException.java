package com.project.grocery.exception;

import org.hibernate.service.spi.ServiceException;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 15, 2018
 * 
 */
@SuppressWarnings("serial")
public class ExpireException extends ServiceException {

	/**
	 * @param message
	 */
	public ExpireException(String message) {
		super(message);
	}

}
