package com.project.grocery.service;


import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.grocery.dto.LoginResponceDto;
import com.project.grocery.exception.LoginFailException;
import com.project.grocery.exception.LogoutFailException;
import com.project.grocery.model.Login;
import com.project.grocery.repository.LoginRepository;
import com.project.grocery.util.LoginStatus;
import com.project.grocery.util.Md5Hashing;
import com.project.grocery.util.Status;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@Service
public class LoginService {
	
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
		
		if (userId != null) {
			Login user = loginRepository.findLoginById(userId);
			if (user == null) {
				throw new LogoutFailException("User id mismatch");
			}
			user.setLoginStatus(LoginStatus.LOGOUT);
			loginRepository.save(user);
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

}
