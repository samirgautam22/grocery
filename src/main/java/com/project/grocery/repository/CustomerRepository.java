package com.project.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.grocery.model.Customer;
import com.project.grocery.util.Status;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer ,Long> {

	/**
	 * @param email
	 * @return
	 */
	Customer findByEmailAndStatusNot(String email,Status delete);

	/**
	 * @param id
	 * @return
	 */
	Customer findCustomerById(long id);

	/**
	 * @param username
	 * @param delete
	 * @return
	 */
	Customer findByUsernameAndStatusNot(String username, Status delete);

}
