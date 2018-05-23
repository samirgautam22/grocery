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
public class StoreAddress implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String state;
	private String district;
	private String vdc;
	private Long wardNo;
	private String HomeNo;
	private String wardName;

	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	
	
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
	 * @return the homeNo
	 */
	public String getHomeNo() {
		return HomeNo;
	}



	/**
	 * @param homeNo the homeNo to set
	 */
	public void setHomeNo(String homeNo) {
		HomeNo = homeNo;
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
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * @param store
	 *            the store to set
	 */
	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * @return the wardName
	 */
	public String getWardName() {
		return wardName;
	}

	/**
	 * @param wardName
	 *            the wardName to set
	 */
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}

}
