package com.project.grocery.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 23, 2018
 * 
 */
@SuppressWarnings("serial")
@Entity
public class State implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String state;
	
	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private List<District> district;
	
	/**
	 * @param id2
	 */
	public State(Long id) {
		this.id=id;
	}
	/**
	 * 
	 */
	public State() {
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
	public List<District> getDistrict() {
		return district;
	}
	/**
	 * @param district the district to set
	 */
	public void setDistrict(List<District> district) {
		this.district = district;
	}
	

}
