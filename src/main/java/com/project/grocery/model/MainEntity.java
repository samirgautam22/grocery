package com.project.grocery.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.project.grocery.util.Status;


/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 3, 2018
 * 
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class MainEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	protected Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modify_date")
	protected Date modifyDate;

	@ManyToOne
	@JoinColumn(name = "created_by")
	protected User createdBy;

	@ManyToOne
	@JoinColumn(name = "modify_by")
	protected User modifyBy;

	@Enumerated(EnumType.STRING)
	protected Status status;

	

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate
	 *            the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	
	/**
	 * @return the createdBy
	 */
	public User getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the modifyBy
	 */
	public User getModifyBy() {
		return modifyBy;
	}

	/**
	 * @param modifyBy the modifyBy to set
	 */
	public void setModifyBy(User modifyBy) {
		this.modifyBy = modifyBy;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

}
