package com.project.grocery.request;

import java.io.Serializable;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 20, 2018
 * 
 */
@SuppressWarnings("serial")
public class ItemsCreatationRequest implements Serializable {
	
	private String itemsName;
	private String itemsPicture;
	
	
	

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
	 * @return the itemsName
	 */
	public String getItemsName() {
		return itemsName;
	}
	/**
	 * @param itemsName the itemsName to set
	 */
	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}

}
