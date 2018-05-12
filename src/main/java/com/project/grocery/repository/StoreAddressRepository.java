package com.project.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.grocery.model.StoreAddress;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 12, 2018
 * 
 */
@Repository
public interface StoreAddressRepository extends JpaRepository<StoreAddress,Long> {

	/**
	 * @param id
	 * @return
	 */
	StoreAddress findStoreAddressById(Long id);

}
