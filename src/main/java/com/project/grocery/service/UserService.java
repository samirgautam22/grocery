package com.project.grocery.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.grocery.exception.AlreadyExitException;
import com.project.grocery.exception.NotFoundException;
import com.project.grocery.exception.RequiredException;
import com.project.grocery.exception.ValidationException;
import com.project.grocery.model.Login;
import com.project.grocery.model.User;
import com.project.grocery.repository.LoginRepository;
import com.project.grocery.repository.UserRepository;
import com.project.grocery.request.PasswordEditRequest;
import com.project.grocery.request.UserCreationRequest;
import com.project.grocery.request.UserEditRequest;
import com.project.grocery.util.LoginStatus;
import com.project.grocery.util.LoginType;
import com.project.grocery.util.Status;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@Service
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	@Autowired
	UserRepository userRepository;

	@Autowired
	LoginRepository loginRepository;

	@Autowired
	LoginService loginService;

	/**
	 * @param userDto
	 */
	@Transactional
	public User saveUser(Long userId, UserCreationRequest userDto) {
		LOG.debug("User Creation...");
		Login l = loginRepository.findLoginByUsername(userDto.getUsername());
		if (l != null && !l.getUser().getStatus().equals(Status.DELETE)) {
			throw new AlreadyExitException("Username Already Exits");
		}

		User u = userRepository.findByEmailAndStatusNot(userDto.getEmail(), Status.DELETE);

		if (u != null) {
			throw new AlreadyExitException("Email already Exits !!");
		}
		User user = new User();
		user.setFullName(userDto.getFullName());
		user.setEmail(userDto.getEmail());
		user.setGender(userDto.getGender());
		user.setPhoneNo(userDto.getPhoneNo());
		user.setCreatedDate(new Date());
		user.setCreatedBy(new User(userId));
		user.setUsername(userDto.getUsername());
		user.setUserRole(userDto.getUserRole());
		user.setStatus(Status.ACTIVE);

		LOG.debug("User Adding");
		User savedUser = userRepository.save(user);
		LOG.debug("User Added");

		if (user != null) {
			Login login = new Login();

			login.setLoginStatus(LoginStatus.LOGOUT);
			login.setPassword(userDto.getPassword());
			login.setCreatedDate(new Date());
			login.setUsername(userDto.getUsername());
			login.setUser(savedUser);
			login.setLoginType(LoginType.CUSTOMER);
			login.setStatus(Status.ACTIVE);
			loginService.saveLogin(login);

		}

		return user;
	}

	/**
	 * @param id
	 */
	@Transactional
	public void deleteUser(Long id) {
		LOG.debug("Deleting user");
		User user = userRepository.findUserById(id);
		if (user == null) {
			throw new NotFoundException("User not found");

		}
		user.setStatus(Status.DELETE);
		LOG.debug("User Deleted");
		userRepository.save(user);
	}

	/**
	 * @param userEditRequest
	 */
	@Transactional
	public User editUser(UserEditRequest userEditRequest) {

		LOG.debug("Request For user Edit");
		if (userEditRequest.getId() == null) {
			throw new RequiredException("User id is needed");
		}
		User user = userRepository.findUserById(userEditRequest.getId());

		//User u = userRepository.findByUsernameAndStatusNot(userEditRequest.getUsername(), Status.DELETE);

//		if (u != null) {
//			throw new AlreadyExitException("Username Already Eits");
//		}

		if (user == null) {
			throw new NotFoundException("User not foud");
		}

		if (userEditRequest.getEmail() != null) {
			emailDuplication(userEditRequest.getEmail(), user);
		}

		if (userEditRequest.getFullName() != null) {
			user.setFullName(userEditRequest.getFullName());
		}

		if (userEditRequest.getEmail() != null) {
			user.setEmail(userEditRequest.getEmail());
		}

		if (userEditRequest.getGender() != null) {
			user.setGender(userEditRequest.getGender());
		}

		if (userEditRequest.getUsername() != null) {
			user.setUsername(userEditRequest.getUsername());
		}

		if (userEditRequest.getPhoneNo() != null) {
			user.setPhoneNo(userEditRequest.getPhoneNo());
		}

		if (userEditRequest.getUserRole() != null)
			user.setUserRole(userEditRequest.getUserRole());

		user.setModifyDate(new Date());
		User userSave = userRepository.save(user);
		LOG.debug("User Edited");
		return userSave;

	}

	/**
	 * @param userId
	 * @param passwordEditRequest
	 */

	private void emailDuplication(String email, User user) {

		User u = userRepository.findByEmailAndStatusNot(email, Status.DELETE);
		if (u != null && user.getId().equals(u.getId())) {

			throw new AlreadyExitException("Email Already Exit");

		}
	}

	@Transactional
	public void changePassword(Long userId, PasswordEditRequest passwordEditRequest) {

		LOG.debug("Request Acccepted to change password");
		if (!passwordEditRequest.getNewPassword().equals(passwordEditRequest.getConfirmNewPassword())) {
			throw new ValidationException("New password and confrom password doesnt match");

		}

		Login login = loginRepository.findByUsername(passwordEditRequest.getUsername());
		if (login == null) {
			throw new ValidationException("Email not found");

		}
		if (!userId.equals(login.getUser().getId())) {
			throw new ValidationException("You are not authorized");
		}

		if (!passwordEditRequest.getOldPassword().equals(login.getPassword())) {
			throw new ValidationException("Old Password not match");
		}
		login.setPassword(passwordEditRequest.getNewPassword());
		loginRepository.save(login);
		LOG.debug("Password Changed");
	}

}
