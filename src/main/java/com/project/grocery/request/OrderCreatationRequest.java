package com.project.grocery.request;

import java.io.Serializable;
import java.util.List;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 13, 2018
 * 
 */
@SuppressWarnings("serial")
public class OrderCreatationRequest implements Serializable {

	private List<OrderNameCreatation> orderName;
	
	
	
	/**
	 * @return the orderName
	 */
	public List<OrderNameCreatation> getOrderName() {
		return orderName;
	}
	/**
	 * @param orderName the orderName to set
	 */
	public void setOrderName(List<OrderNameCreatation> orderName) {
		this.orderName = orderName;
	}
	
	
	
	
	

}
