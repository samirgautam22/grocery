package com.project.grocery.util;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
public enum LoginStatus {
	
	LOGGEDIN("LOGGEDIN", "loggedin"), 
	LOGOUT("LOGOUT", "logout");

	private final String value;
	private final String description;

	

	public String value() {
		return this.value;
	}

	/**
	 * @param value
	 * @param description
	 */
	private LoginStatus(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getReasonPhrase() {
		return this.description;
	}

	@Override
	public String toString() {
		return this.value;
	}


}
