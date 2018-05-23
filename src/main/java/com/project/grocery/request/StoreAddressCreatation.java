package com.project.grocery.request;

import java.io.Serializable;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 12, 2018
 * 
 */
@SuppressWarnings("serial")
public class StoreAddressCreatation implements Serializable {

	private String state;
	private String district;
	private String vdc;
	private Long wardNo;
	private String wardName;
	private String homeNo;
	
	
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
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}
	/**
	 * @param district the district to set
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
	 * @param vdc the vdc to set
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
	 * @param wardNo the wardNo to set
	 */
	public void setWardNo(Long wardNo) {
		this.wardNo = wardNo;
	}
	/**
	 * @return the wardName
	 */
	public String getWardName() {
		return wardName;
	}
	/**
	 * @param wardName the wardName to set
	 */
	public void setWardName(String wardName) {
		this.wardName = wardName;
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
