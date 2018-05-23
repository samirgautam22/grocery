package com.project.grocery.exception;

import org.hibernate.service.spi.ServiceException;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 23, 2018
 * 
 */
@SuppressWarnings("serial")
public class NotValideExpection extends ServiceException {

	/**
	 * @param message
	 */
	public NotValideExpection(String message) {
		super(message);
	}

}
