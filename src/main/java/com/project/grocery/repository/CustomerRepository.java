package com.project.grocery.repository;

import java.util.List;

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
	 * @param delete
	 * @return
	 */
	List<Customer> findAllCustomerByStatusNot(Status delete);

	/**
	 * @param customerId
	 * @param delete
	 * @return
	 */
	Customer findCustomerByIdAndStatusNot(Long customerId, Status delete);

	/**
	 * @param email
	 * @param delete
	 * @return
	 */
	Customer findByEmailAndStatusNot(String email, Status delete);

	/**
	 * @param id
	 * @return
	 */
	Customer findCustomerById(Long id);

	/**
	 * @param username
	 * @param delete
	 * @return
	 */
	Customer findByUsernameAndStatusNot(String username, Status delete);

	/**
	 * @param customerId
	 * @param delete
	 * @return
	 */
	Customer findByIdAndStatusNot(Long customerId, Status delete);

	/**
	 * @param customer
	 * @return
	 */
	Customer findCustomerById(Customer customer);

}
