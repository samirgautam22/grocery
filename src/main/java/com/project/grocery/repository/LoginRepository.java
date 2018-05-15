package com.project.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.grocery.model.Login;
import com.project.grocery.util.Status;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@Repository
public interface LoginRepository extends JpaRepository<Login,Long> {

	/**
	 * @param username
	 * @param delete
	 * @return
	 */
	 Login findByUsernameAndStatusNot(String username, Status delete);

	
	/**
	 * @param username
	 * @return
	 */
	Login findLoginByUsername(String username);

	/**
	 * @param username
	 * @return
	 */
	Login findByUsername(String username);


	/**
	 * @param userId
	 * @return
	 */
	Login findLoginById(Long userId);


	/**
	 * @param email
	 * @param delete
	 * @return
	 */
	Login findLoginByEmailAndStatusNot(String email, Status delete);


	/**
	 * @param email
	 * @param blocked
	 * @return
	 */
	Login findLoginByEmailAndStatus(String email, Status blocked);

}
