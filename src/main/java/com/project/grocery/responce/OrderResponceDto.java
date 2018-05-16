package com.project.grocery.responce;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 16, 2018
 * 
 */
@SuppressWarnings("serial")
public class OrderResponceDto implements Serializable{

	private Long id;
	private double price;
	private Long item;
	private String orderName;
	private Date orderDate;
	private String orderBy;
	List<AddressResponceDto> address;
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
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}
	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * @return the address
	 */
	public List<AddressResponceDto> getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(List<AddressResponceDto> address) {
		this.address = address;
	}
	
	
}
