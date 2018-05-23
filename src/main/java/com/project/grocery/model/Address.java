package com.project.grocery.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 3, 2018
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Address implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String state;
	private String district;
	private String vdc;
	private Long wardNo;
	private String wardName;
	private String homeNo;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	
	

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the wardname
	 */
	public String getWardName() {
		return wardName;
	}

	/**
	 * @param wardname the wardname to set
	 */
	public void setWardName(String wardname) {
		this.wardName = wardname;
	}

	/**
	 * @return the id
	 */
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
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district
	 *            the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the vdc
	 */
	public String getVdc() {
		return vdc;
	}

	/**
	 * @param vdc
	 *            the vdc to set
	 */
	public void setVdc(String vdc) {
		this.vdc = vdc;
	}

	/**
	 * @return the wardNo
	 */
	public Long getWardNo() {
		return wardNo;
	}

	/**
	 * @param wardNo
	 *            the wardNo to set
	 */
	public void setWardNo(Long wardNo) {
		this.wardNo = wardNo;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return the homeNo
	 */
	public String getHomeNo() {
		return homeNo;
	}

	/**
	 * @param homeNo the homeNo to set
	 */
	public void setHomeNo(String homeNo) {
		this.homeNo = homeNo;
	}

	
	

}
