package com.project.grocery.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.grocery.dto.LoginResponceDto;
import com.project.grocery.exception.ExpireException;
import com.project.grocery.exception.LoginFailException;
import com.project.grocery.exception.LogoutFailException;
import com.project.grocery.exception.NotFoundException;
import com.project.grocery.exception.VerificationException;
import com.project.grocery.model.Login;
import com.project.grocery.model.Verification;
import com.project.grocery.repository.LoginRepository;
import com.project.grocery.repository.VerificationRepository;
import com.project.grocery.request.ForgetPasswordRequest;
import com.project.grocery.util.DateUtil;
import com.project.grocery.util.EmailUtility;
import com.project.grocery.util.LoginStatus;
import com.project.grocery.util.Status;
import com.project.grocery.util.TokenGenerator;
import com.project.grocery.util.VerificationStatus;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@Service
public class LoginService {
	private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);
	@Autowired
	LoginRepository loginRepository;

	@Autowired
	VerificationRepository verificationRepository;

	@Autowired
	VerificationService verificationService;

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
		Login login = loginRepository.findByUsernameAndStatusNot(username, Status.DELETE);
		if (login == null) {

			throw new LoginFailException("Sorry,Username not found !!");
		}
		
		Login l=loginRepository.findByUsernameAndStatus(username, Status.BLOCKED);
		if(l!=null) {
			throw new VerificationException("Sorry Your Account is not verified Check Your Email");
		}
		
		
		
		
		if (password.equals(login.getPassword())) {
			login.setLastlogin(new Date());
			login.setLoginStatus(LoginStatus.LOGGEDIN);
			login.setDeviceId(deviceId);
			LoginResponceDto responce = getLoginResponce(login);
			LOG.debug("Login Accepted");
			return responce;
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
	@Transactional
	public void saveLogin(Login login) {

		loginRepository.save(login);
	}

	/**
	 * @param email
	 */
	@Transactional
	public void resetPassword(String email) {
		LOG.debug("Request to reset Password");
		Login login = loginRepository.findLoginByEmailAndStatusNot(email, Status.DELETE);
		if (login == null) {
			throw new NotFoundException("Email Not found!!");
		}

		TokenGenerator tg = new TokenGenerator();
		String token = tg.generateToken(login.getUsername());

		Verification verification = verificationRepository.findVerificationByEmailAndStatusNot(email,
				VerificationStatus.EXPIRE);

		if (verification != null) {
			verification.setCreatedDate(new Date());
			verification.setExpeireDate(DateUtil.getTokenExpireDate(new Date()));
			verification.setToken(token);
			verification.setStatus(VerificationStatus.ACTIVE);
			verificationService.saveVerification(verification);
		} else {
			Verification verifiy = new Verification();
			verifiy.setEmail(login.getEmail());
			verifiy.setCreatedDate(new Date());
			verifiy.setExpeireDate(DateUtil.getTokenExpireDate(new Date()));
			verifiy.setToken(token);
			verifiy.setStatus(VerificationStatus.ACTIVE);
			verificationService.saveVerification(verifiy);
		}
		EmailUtility.sendResetLink(login.getEmail(), token);
		LOG.debug("Request to reset Password accepted");
	}

	/**
	 * @param forgetPasswordRequest
	 */
	@Transactional
	public void resetForgetPassword(String token, ForgetPasswordRequest forgetPasswordRequest) {

		LOG.debug("Acceped to reset password");

		Verification v = verificationRepository.findVerificationByTokenAndStatusNot(token,VerificationStatus.EXPIRE);
		if (v == null) {
			throw new ExpireException("The session in invallied");
		}

		if (DateUtil.compareDate(v.getCreatedDate(), v.getExpeireDate()) == false) {
			v.setStatus(VerificationStatus.EXPIRE);
			verificationService.saveVerification(v);
			throw new ExpireException("Sorry !! Token is expired");
		}

		Login login = loginRepository.findLoginByEmailAndStatusNot(v.getEmail(), Status.DELETE);

		if (login == null) {
			throw new NotFoundException("Email Address Not found !!");
		}

		if (!forgetPasswordRequest.getNewPassword().equals(forgetPasswordRequest.getConfromPassword())) {
			throw new NotFoundException("Password Did not match");
		}
		login.setPassword(forgetPasswordRequest.getConfromPassword());
		Login savedlogin = loginRepository.save(login);
		if (savedlogin != null) {
			v.setStatus(VerificationStatus.EXPIRE);
			verificationService.saveVerification(v);
		}

		LOG.debug("Password is reset");
	}

}
