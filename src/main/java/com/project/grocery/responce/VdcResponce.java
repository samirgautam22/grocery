package com.project.grocery.responce;

import java.io.Serializable;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 25, 2018
 * 
 */
@SuppressWarnings("serial")
public class VdcResponce implements Serializable {
	
	private String vdc;

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
	

}
