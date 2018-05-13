package com.project.grocery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.grocery.model.Store;
import com.project.grocery.util.Status;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 12, 2018
 * 
 */
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	/**
	 * @param phoneNo
	 * @param delete
	 * @return
	 */
	Store findByPhoneNoAndStatusNot(Long phoneNo, Status delete);


	/**
	 * @param id
	 * @param delete
	 * @return
	 */
	Store findStoreByIdAndStatusNot(Long id, Status delete);


	/**
	 * @param email
	 * @param delete
	 * @return
	 */
	Store findByEmailAndStatusNot(String email, Status delete);


	/**
	 * @param delete
	 * @return
	 */
	List<Store> findAllStoreByStatusNot(Status delete);


	/**
	 * @param storeId
	 * @param delete
	 * @return
	 */
	Store findByIdAndStatusNot(Long storeId, Status delete);


	
	
}
