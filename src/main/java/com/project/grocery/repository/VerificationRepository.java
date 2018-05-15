package com.project.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.grocery.model.Verification;
import com.project.grocery.util.VerificationStatus;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 15, 2018
 * 
 */
@Repository
public interface VerificationRepository extends JpaRepository<Verification,Long> {

	/**
	 * @param token
	 * @param expire
	 * @return
	 */
	Verification findVerificationByTokenAndStatusNot(String token, VerificationStatus expire);

	/**
	 * @param email
	 * @param expire
	 * @return
	 */
	Verification findVerificationByEmailAndStatusNot(String email, VerificationStatus expire);


}
