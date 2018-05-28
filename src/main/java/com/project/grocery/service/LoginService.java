package com.project.grocery.service;

import java.security.NoSuchAlgorithmException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.project.grocery.util.LoginType;
import com.project.grocery.util.Md5Hashing;
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
	
	@Value("${grocery.token.expire.enable}")
	private String tokenExpireEnable;

	@Value("${grocery.token.expire.after}")
	private int tokenExpireAfter;

	@Value("${grocery.login.password.length}")
	private int passwordLength;

	 @Autowired
	  HttpServletRequest request;

   

    private  String getClientIp() {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

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

		Login l = loginRepository.findByUsernameAndStatus(username, Status.BLOCKED);
		if (l != null) {
			throw new VerificationException("Sorry Your Account is not verified Check Your Email");
		}

		try {
			if (Md5Hashing.getPw(password).equals(login.getPassword())) {
				login.setLastlogin(new Date());
				login.setLoginStatus(LoginStatus.LOGGEDIN);
				
				String ip=getClientIp();
				System.out.println("my ip="+ip);
				Client client = ClientBuilder.newClient();
				  Response response = client.target("https://api.ipdata.co/")
				    .request(MediaType.TEXT_PLAIN_TYPE)
				    .header("Accept", "application/json")
				    .get();
				  
				  System.out.println("body:" + response.readEntity(String.class));
				
				
				login.setDeviceId(deviceId);
				login.setToken(TokenGenerator.getToken());
				if (tokenExpireAfter > 0) {
					login.setTokenExpirationDateTime(
							DateUtil.currentDateTimePlusMinutes(tokenExpireAfter));
				}
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
		LoginResponceDto loginResponceDto=new LoginResponceDto();
		if(login.getLoginType().equals(LoginType.CUSTOMER)) {
			loginResponceDto=new LoginResponceDto.Builder().id(login.getCustomer().getId())
					.token(login.getToken()).build();
		}
		return loginResponceDto;
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
			user.setToken("");
			user.setTokenExpirationDateTime(new Date());
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

		Verification v = verificationRepository.findVerificationByTokenAndStatusNot(token, VerificationStatus.EXPIRE);
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
		try {
			login.setPassword(Md5Hashing.getPw(forgetPasswordRequest.getConfromPassword()));
			Login savedlogin = loginRepository.save(login);
			if (savedlogin != null) {
				v.setStatus(VerificationStatus.EXPIRE);
				verificationService.saveVerification(v);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		LOG.debug("Password is reset");
	}

	/**
	 * @param token
	 */
	public void chekToken(String token) {
		Login login=loginRepository.findByToken(token);
		if(login==null) {
			throw new NotFoundException("Token is invallied");
		}
		if (!DateUtil.isCurrentTimeBeforeThanGivenTime(login.getTokenExpirationDateTime()))
			throw new ExpireException("token Expired");

	
}

	/**
	 * @param loginId
	 * @param token
	 * @return
	 */
	public boolean isValidToken(Long loginId, String token) {
		
		if(loginId==null || token==null) {
			return false;
		}
		Login login = loginRepository.findByIdAndToken(loginId, token);
		if (null == login) {
			return false;
		}
		LOG.debug("Login found.");
		if (null != tokenExpireEnable) {
			if (tokenExpireEnable.equalsIgnoreCase("ENABLE")) {
				if (!DateUtil.isCurrentTimeBeforeThanGivenTime(
						login.getTokenExpirationDateTime()))
					return false;
			}
		}
		return true;
	}
	}
	
//	public boolean isValidToken(Long userId, String token) {
//		if (userId.equals(0L)) {
//			throw new ServiceException("userId or loginId required in header parameter.");
//		}
//		if (null == userId || null == token) {
//			return false;
//		}
//		// Login user = userRepository.getOne(userId);
//		LOG.debug("User id {} and token {}", userId, token);
//		// Login login = loginRepository.findByIdAndToken(user.getLoginId(), token);
//		LoginToken login = loginTokenRepository.findByLoginIdAndToken(userId, token);
//		if (null == login) {
//			return false;
//		}
//		LOG.debug("Login found.");
//		if (tokenExpireEnable.equalsIgnoreCase(Constant.ENABLE)) {
//			if (!DateUtils.isCurrentTimeBeforeThanGivenTime(login.getTokenExpirationDateTime()))
//				return false;
//		}
//		return true;
//	}

