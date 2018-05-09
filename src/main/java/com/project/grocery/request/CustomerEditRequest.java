package com.project.grocery.request;

import java.io.Serializable;
import java.util.List;

import com.project.grocery.util.UserRoles;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 6, 2018
 * 
 */
@SuppressWarnings("serial")
public class CustomerEditRequest implements Serializable {

	private String fullName;
	private String gender;
	private String email;
	private Long phoneNo;
	private String username;
	private UserRoles userRole;
	private Long id;
	List<AddressEditRequest> address;

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the address
	 */
	public List<AddressEditRequest> getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(List<AddressEditRequest> address) {
		this.address = address;
	}

}
