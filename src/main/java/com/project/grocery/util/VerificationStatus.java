package com.project.grocery.util;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 15, 2018
 * 
 */
public enum VerificationStatus{
	
	ACTIVE("ACTIVE", "Active Status"),

	EXPIRE("EXPIRE", "Expire Status");

	private final String value;
	private final String description;

	private VerificationStatus(String value, String description) {
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
