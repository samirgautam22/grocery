package com.project.grocery.request;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 13, 2018
 * 
 */
@SuppressWarnings("serial")
public class OrderCreatationRequest implements Serializable {

	@JsonIgnore
	private Long id;
	private double price;
	private Long item;
	private String orderName;
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
	 * @return the item
	 */
	public Long getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(Long item) {
		this.item = item;
	}
	/**
	 * @return the orderName
	 */
	public String getOrderName() {
		return orderName;
	}
	/**
	 * @param orderName the orderName to set
	 */
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	/**
	 * @return the totalPrice
	 */
	

}
