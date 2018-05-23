package com.project.grocery.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.grocery.model.Verification;
import com.project.grocery.repository.VerificationRepository;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 15, 2018
 * 
 */
@Service
public class VerificationService {
	
	@Autowired
	VerificationRepository  verificationRepository;
	
	@Transactional
	public Verification saveVerification(Verification verification) {
		return verificationRepository.save(verification);
		
	}

}
