package com.project.grocery.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;


/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 3, 2018
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "customer")
public class Customer extends MainEntity implements Serializable {
	@Column(name = "full_name")
	private String fullName;
	private String gender;
	private String email;
	@Column(name = "phone_no")
	private Long phoneNo;
	@Size(min = 5, max = 30, message = "Username must between 5 and 30")
	private String username;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Address> address;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Login> logins;
	
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Order> order;

	/**
	 * 
	 */
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(Long id) {
		this.id = id;
	}

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
	 * @return the address
	 */
	public List<Address> getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(List<Address> address) {
		this.address = address;
	}

	/**
	 * @return the logins
	 */
	public List<Login> getLogins() {
		return logins;
	}

	/**
	 * @param logins
	 *            the logins to set
	 */
	public void setLogins(List<Login> logins) {
		this.logins = logins;
	}

	/**
	 * @return the order
	 */
	public List<Order> getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(List<Order> order) {
		this.order = order;
	}

	

}
