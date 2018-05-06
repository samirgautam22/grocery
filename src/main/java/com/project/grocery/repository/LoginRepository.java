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
	 * @param userId
	 * @return
	 */
	Login findByUserId(Long userId);

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

}
