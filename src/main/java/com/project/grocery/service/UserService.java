package com.project.grocery.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.grocery.dto.UserDto;
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
import com.project.grocery.responce.UserResponceDto;
import com.project.grocery.util.LoginStatus;
import com.project.grocery.util.LoginType;
import com.project.grocery.util.Md5Hashing;
import com.project.grocery.util.Status;
import com.project.grocery.util.UserRoles;

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
		if (l != null ) {
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
		user.setCreatedBy(userId);
		user.setUsername(userDto.getUsername());
		user.setUserRole(UserRoles.ADMIN);
		user.setStatus(Status.ACTIVE);

		LOG.debug("User Adding");
		User savedUser = userRepository.save(user);
		LOG.debug("User Added");

		if (user != null) {
			Login login = new Login();

			
			try {
				login.setPassword(Md5Hashing.getPw(userDto.getPassword()));
				login.setLoginStatus(LoginStatus.LOGOUT);
				login.setCreatedDate(new Date());
				login.setUsername(userDto.getUsername());
				login.setEmail(userDto.getEmail());
				login.setUser(savedUser);
				login.setLoginType(LoginType.ADMIN);
				login.setStatus(Status.ACTIVE);
				loginService.saveLogin(login);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			

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
		Login l=loginRepository.findLoginByEmailAndStatusNot(user.getEmail(),Status.DELETE);
		if(l==null) {
			throw new NotFoundException("Customer Not found !!");
		}
		l.setStatus(Status.DELETE);
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

		User u = userRepository.findByUsernameAndStatusNot(userEditRequest.getUsername(), Status.DELETE);

		if (u != null) {
			throw new AlreadyExitException("Username Already Eits");
		}

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
			throw new ValidationException("Username not found");

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

	
	/**
	 * @return
	 */
	public List<UserDto> listAllUsers() {
		LOG.debug("Request Accepted to list all Users");
		List<User> users=userRepository.findAllUserByStatusNot(Status.DELETE);
		List<UserDto> user=new ArrayList<>();
		if(users==null) {
			throw new NotFoundException("User Not found!!");
		}
		users.parallelStream().forEach(u->{
			UserDto userDto=new UserDto();
			userDto.setId(u.getId());
			userDto.setFullName(u.getFullName());
			userDto.setEmail(u.getEmail());
			userDto.setGender(u.getGender());
			userDto.setPhoneNo(u.getPhoneNo());
			userDto.setUsername(u.getUsername());
			user.add(userDto);
		});
		LOG.debug("All User Are obtain");
		
		return user;
	}

	/**
	 * @param userId
	 * @return
	 */
	public UserResponceDto getUser(Long userId) {
		LOG.debug("Request Accepted for List A user");
		User users=userRepository.findUserByIdAndStatusNot(userId,Status.DELETE);
		if(users==null) {
			throw new NotFoundException("User not found ");
		}
		UserResponceDto userResponceDto=new UserResponceDto();
		userResponceDto.setId(users.getId());
		userResponceDto.setFullName(users.getFullName());
		userResponceDto.setEmail(users.getEmail());
		userResponceDto.setGender(users.getGender());
		userResponceDto.setPhoneNo(users.getPhoneNo());
		userResponceDto.setUsername(users.getUsername());
		LOG.debug("User obtain");
		return userResponceDto;
	}

	

}
