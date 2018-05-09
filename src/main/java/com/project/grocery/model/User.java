package com.project.grocery.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.project.grocery.util.UserRoles;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name="user")
public class User extends MainEntity implements Serializable {
	
	
	@Column(name = "full_name")
	private String fullName;
	private String gender;
	private String email;
	@Column(name = "phone_no")
	private Long phoneNo;
	@Size(min = 5, max = 30, message = "Username must between 5 and 30")
	private String username;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private UserRoles userRole;

	@OneToMany(mappedBy = "user")
	private List<Login> logins;
	

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
	 * 
	 */
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(Long id) {
		
		this.id=id;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
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
	 * @param gender the gender to set
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
	 * @param email the email to set
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
	 * @param phoneNo the phoneNo to set
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
	 * @param username the username to set
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
	 * @param userRole the userRole to set
	 */
	public void setUserRole(UserRoles userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return the logins
	 */
	public List<Login> getLogins() {
		return logins;
	}

	/**
	 * @param logins the logins to set
	 */
	public void setLogins(List<Login> logins) {
		this.logins = logins;
	}
	
	
	

}
