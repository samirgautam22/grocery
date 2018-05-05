package com.project.grocery.util;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
public enum LoginType {
	
	ADMIN("ADMIN", "admin"),

	CUSTOMER("CUSTOMER", "customer"),
	
	STOER("STORE","store");
	
	private final String value;
	private final String description;

	

	
	
	/**
	 * @param value
	 * @param description
	 */
	private LoginType(String value, String description) {
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
