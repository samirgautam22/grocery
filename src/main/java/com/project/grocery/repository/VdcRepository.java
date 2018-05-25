package com.project.grocery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.grocery.model.District;
import com.project.grocery.model.Vdc;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 24, 2018
 * 
 */
@Repository
public interface VdcRepository extends JpaRepository<Vdc,Long> {

	/**
	 * @param string
	 * @return
	 */
	Vdc findByVdc(String string);

	/**
	 * @param district
	 * @return
	 */
	List<Vdc> findAllVdcByDistrict(District district);

}
