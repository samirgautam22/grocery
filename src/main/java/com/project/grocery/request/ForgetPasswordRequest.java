package com.project.grocery.request;

import java.io.Serializable;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 15, 2018
 * 
 */
@SuppressWarnings("serial")
public class ForgetPasswordRequest implements Serializable {
	
	private String newPassword;
	private String confromPassword;
	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	/**
	 * @return the confromPassword
	 */
	public String getConfromPassword() {
		return confromPassword;
	}
	/**
	 * @param confromPassword the confromPassword to set
	 */
	public void setConfromPassword(String confromPassword) {
		this.confromPassword = confromPassword;
	}
	
	
	

}
