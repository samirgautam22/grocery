package com.project.grocery.dto;

import java.io.Serializable;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 11, 2018
 * 
 */
@SuppressWarnings("serial")
public class AddressDto implements Serializable {

	private String zone;
	private String district;
	private String vdc;
	private Long wardNo;
	private String homeNo;
	private String wardName;
	private Long id;
	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}
	/**
	 * @param zone the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
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
	
	

}
