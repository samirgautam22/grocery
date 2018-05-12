package com.project.grocery.request;

import java.io.Serializable;
import java.util.List;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 12, 2018
 * 
 */
@SuppressWarnings("serial")
public class StoreEditRequest implements Serializable {
	private Long id;
	private String storeName;
	private Long phoneNo;
	private Long panNo;
	private String email;
	private String username;
	private List<StoreAddressEdit> storeAddressEdit;
	
	
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
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}
	/**
	 * @param storeName the storeName to set
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
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
	 * @return the panNo
	 */
	public Long getPanNo() {
		return panNo;
	}
	/**
	 * @param panNo the panNo to set
	 */
	public void setPanNo(Long panNo) {
		this.panNo = panNo;
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
	 * @return the storeAddressEdit
	 */
	public List<StoreAddressEdit> getStoreAddressEdit() {
		return storeAddressEdit;
	}
	/**
	 * @param storeAddressEdit the storeAddressEdit to set
	 */
	public void setStoreAddressEdit(List<StoreAddressEdit> storeAddressEdit) {
		this.storeAddressEdit = storeAddressEdit;
	}
	
	
	
}
