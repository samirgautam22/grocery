package com.project.grocery.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 23, 2018
 * 
 */
@SuppressWarnings("serial")
@Entity
public class District implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String district;
	
	
	@ManyToOne
	@JoinColumn(name="state_id")
	private State state;
	
	@OneToMany(mappedBy="district",fetch=FetchType.LAZY)
	private List<Vdc> vdc;
	
	
	/**
	 * @return the vdc
	 */
	public List<Vdc> getVdc() {
		return vdc;
	}
	/**
	 * @param vdc the vdc to set
	 */
	public void setVdc(List<Vdc> vdc) {
		this.vdc = vdc;
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
	 * @return the state
	 */
	public State getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	

}
