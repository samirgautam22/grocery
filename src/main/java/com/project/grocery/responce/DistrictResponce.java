package com.project.grocery.responce;

import java.io.Serializable;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 25, 2018
 * 
 */
@SuppressWarnings("serial")
public class DistrictResponce implements Serializable {
	
	private String district;

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
	

}
