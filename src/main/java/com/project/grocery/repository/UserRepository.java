package com.project.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.grocery.model.User;
import com.project.grocery.util.Status;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	/**
	 * @param email
	 * @param delete
	 * @return
	 */
	User findByEmailAndStatusNot(String email, Status Status);

	/**
	 * @param id
	 * @return
	 */
	User findUserById(Long id);


	/**
	 * @param username
	 * @param delete
	 * @return
	 */
	User findByUsernameAndStatusNot(String username, Status delete);

}
