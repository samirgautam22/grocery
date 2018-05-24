package com.project.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.grocery.model.State;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 24, 2018
 * 
 */
@Repository
public interface StateRepositoty extends JpaRepository<State,Long> {

	/**
	 * @param string
	 * @return
	 */
	State findByState(String string);

}
