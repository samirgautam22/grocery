package com.project.grocery.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 3, 2018
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Store extends MainEntity implements Serializable {

	@Column(name="store_name")
	private String storeName;
	@Column(name="store_address")
	@OneToMany(mappedBy="store",fetch=FetchType.LAZY)
	private List<StoreAddress> storeAddress;
	@Column(name="store_no")
	private Long storeNo;
	@Column(name="phone_no")
	private Long phoneNo;
	private Long panNo;
	
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
	 * @return the storeAddress
	 */
	public List<StoreAddress> getStoreAddress() {
		return storeAddress;
	}
	/**
	 * @param storeAddress the storeAddress to set
	 */
	public void setStoreAddress(List<StoreAddress> storeAddress) {
		this.storeAddress = storeAddress;
	}
	/**
	 * @return the storeNo
	 */
	public Long getStoreNo() {
		return storeNo;
	}
	/**
	 * @param storeNo the storeNo to set
	 */
	public void setStoreNo(Long storeNo) {
		this.storeNo = storeNo;
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
	
	
	

}
