package com.project.grocery.service;


import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.grocery.dto.LoginResponceDto;
import com.project.grocery.exception.LoginFailException;
import com.project.grocery.exception.LogoutFailException;
import com.project.grocery.exception.NotFoundException;
import com.project.grocery.model.Login;
import com.project.grocery.repository.LoginRepository;
import com.project.grocery.util.EmailUtility;
import com.project.grocery.util.LoginStatus;
import com.project.grocery.util.Md5Hashing;
import com.project.grocery.util.RandomStrings;
import com.project.grocery.util.Status;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@Service
public class LoginService {
	private static final Logger LOG = LoggerFactory.getLogger( LoginService.class);
	@Autowired
	LoginRepository loginRepository;

	/**
	 * @param username
	 * @param password
	 * @param active
	 * @param deviceId
	 * @return
	 */
	@Transactional
	public LoginResponceDto logInUser(String username, String password, Status active, String deviceId) {
		LOG.debug("Request for Login");
		Login login=loginRepository.findByUsernameAndStatusNot(username,Status.DELETE);
		if(login==null) {
			
			throw new LoginFailException("Sorry,Username not found !!");
		}
		try {
			if(Md5Hashing.getPw(password).equals(login.getPassword())) {
				login.setLastlogin(new Date());
				login.setLoginStatus(LoginStatus.LOGGEDIN);
				login.setDeviceId(deviceId);
				LoginResponceDto responce = getLoginResponce(login);
				LOG.debug("Login Accepted");
				return responce;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		throw new LoginFailException("Username and Password missmatch");
	}

	/**
	 * @param login
	 * @return
	 */
	private LoginResponceDto getLoginResponce(Login login) {
		return null;
	}

	/**
	 * @param userId
	 * @return 
	 */
	public Login logout(Long userId) {
		LOG.debug("request for logout");
		if (userId != null) {
			Login user = loginRepository.findLoginById(userId);
			if (user == null) {
				throw new LogoutFailException("User id mismatch");
			}
			user.setLoginStatus(LoginStatus.LOGOUT);
			loginRepository.save(user);
			LOG.debug("logout");
			return user;
		}

		return null;
	}

	/**
	 * @param login
	 */
	public void saveLogin(Login login) {
		
		loginRepository.save(login);
	}

	/**
	 * @param email
	 */
	public void resetPassword(String email) {
		LOG.debug("Request to reset Password");
		Login login=loginRepository.findLoginByEmailAndStatusNot(email,Status.DELETE);
		if(login==null) {
			throw new NotFoundException("Email Not found!!");
		}
		String password=RandomStrings.newRandomString();
		EmailUtility.sendNewPassword(email, password);
		login.setPassword(password);
		loginRepository.save(login);
		LOG.debug("Request to reset Password accepted");
	}

}
