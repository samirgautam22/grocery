package com.project.grocery.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 16, 2018
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Items implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="items_name")
	private String itemName;
	
	private double price;
	
	@Column(name="image_url")
	private String itemsPicture;

	@ManyToOne
	@JoinColumn(name="store_id")
	private Store store;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	
	
	
	/**
	 * @return the itemsPicture
	 */
	public String getItemsPicture() {
		return itemsPicture;
	}

	/**
	 * @param itemsPicture the itemsPicture to set
	 */
	public void setItemsPicture(String itemsPicture) {
		this.itemsPicture = itemsPicture;
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
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}


	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	

}
