package com.project.grocery.util;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 19, 2018
 * 
 */
public enum OrderStatus{
	
	
	AVIALIABLE("AVALIABLE","avaliable"),
	
	
	DELIVERED("DELIVERED","delivered"),
	
	DELETE("DELETE","delete");
	
	private final String value;
	private final String description;

	private OrderStatus(String value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Return the String value of this status.
	 */
	public String value() {
		return this.value;
	}

	/**
	 * Return the description of this status.
	 */
	public String getReasonPhrase() {
		return this.description;
	}

	/**
	 * Return a string representation of this status value.
	 */
	@Override
	public String toString() {
		return this.value;
	}


}
