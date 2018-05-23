package com.project.grocery.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.project.grocery.util.Status;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 3, 2018
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Store implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "store_name")
	private String storeName;
	@Column(name = "store_address")
	@OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
	private List<StoreAddress> storeAddress;

	@Column(name = "phone_no")
	private Long phoneNo;
	private Long panNo;
	private String email;
	private String username;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modify_date")
	private Date modifyDate;
	
	@Column(name="createdBy")
	private Long createdBy;

	@Enumerated(EnumType.STRING)
	protected Status status;
	
	@OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
	private List<Order> order;
	
	@OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
	private List<Items> items;

	
	
	
	/**
	 * 
	 */
	public Store() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param userId
	 */
	public Store(Long id) {
		this.id=id;
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
	 * @return the panNo
	 */
	public Long getPanNo() {
		return panNo;
	}

	/**
	 * @param panNo
	 *            the panNo to set
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
	 * @param storeName
	 *            the storeName to set
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
	 * @param storeAddress
	 *            the storeAddress to set
	 */
	public void setStoreAddress(List<StoreAddress> storeAddress) {
		this.storeAddress = storeAddress;
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
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate
	 *            the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	/**
	 * @return the items
	 */
	public List<Items> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<Items> items) {
		this.items = items;
	}
	
	

}
