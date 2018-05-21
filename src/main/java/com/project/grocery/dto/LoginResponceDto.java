package com.project.grocery.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.project.grocery.util.LoginType;
import com.project.grocery.util.Status;
import com.project.grocery.util.UserRoles;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class LoginResponceDto implements Serializable {

	private Long id;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private Long phoneNo;
	private String username;
	private String deviceId;
	private Status status;
	private UserRoles userRole;
	private LoginType loginType;
	private String token;

	
	
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 
	 */
	public LoginResponceDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNo
	 */
	public Long getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo
	 *            the phoneNo to set
	 */
	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the userRole
	 */
	public UserRoles getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(UserRoles userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return the loginType
	 */
	public LoginType getLoginType() {
		return loginType;
	}

	/**
	 * @param loginType
	 *            the loginType to set
	 */
	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public static class Builder {
		private Long id;
		private String token;
		
		
		public Builder id(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder token(String token) {
			this.token = token;
			return this;
		}

		/**
		 * @return
		 */
			public LoginResponceDto build() {
			return new LoginResponceDto(this);
		}
		
		

	}
	
	private LoginResponceDto(Builder builder) {
		this.id = builder.id;
		this.token = builder.token;
	}
}
