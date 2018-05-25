package com.project.grocery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.grocery.model.District;
import com.project.grocery.model.State;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 24, 2018
 * 
 */
@Repository
public interface DistrictRepository extends JpaRepository<District,Long> {

	/**
	 * @param string
	 * @return
	 */
	District findByDistrict(String string);

	/**
	 * @param state
	 * @return
	 */
	List<District> findAllDistrictByState(State state);



}
