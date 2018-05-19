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

import com.project.grocery.dto.CustomerDto;
import com.project.grocery.exception.AlreadyExitException;
import com.project.grocery.exception.ExpireException;
import com.project.grocery.exception.NotFoundException;
import com.project.grocery.exception.RequiredException;
import com.project.grocery.exception.ValidationException;
import com.project.grocery.model.Address;
import com.project.grocery.model.Customer;
import com.project.grocery.model.Login;
import com.project.grocery.model.Verification;
import com.project.grocery.repository.AddressRepository;
import com.project.grocery.repository.CustomerRepository;
import com.project.grocery.repository.LoginRepository;
import com.project.grocery.repository.VerificationRepository;
import com.project.grocery.request.AddressEditRequest;
import com.project.grocery.request.CustomerAddressCreationRequest;
import com.project.grocery.request.CustomerCreationRequest;
import com.project.grocery.request.CustomerEditRequest;
import com.project.grocery.request.PasswordEditRequest;
import com.project.grocery.responce.AddressResponceDto;
import com.project.grocery.responce.CustomerResponceDto;
import com.project.grocery.util.DateUtil;
import com.project.grocery.util.EmailUtility;
import com.project.grocery.util.LoginStatus;
import com.project.grocery.util.LoginType;
import com.project.grocery.util.Md5Hashing;
import com.project.grocery.util.Status;
import com.project.grocery.util.TokenGenerator;
import com.project.grocery.util.VerificationStatus;
import com.project.grocery.dto.AddressDto;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@Service
public class CustomerService {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	LoginRepository loginRepository;

	@Autowired
	LoginService loginService;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	VerificationRepository verificationRepository;

	@Autowired
	VerificationService verificationService;

	/**
	 * @param customerCreationRequest
	 */
	@Transactional
	public Customer saveCustomer(CustomerCreationRequest customerCreationRequest) {
		LOG.debug("Customer Creation started..");
		Login l = loginRepository.findByUsernameAndStatusNot(customerCreationRequest.getUsername(), Status.DELETE);
		if (l != null && !!l.getUser().getStatus().equals(Status.DELETE)) {
			throw new AlreadyExitException("Username Already Exits");
		}

		Customer c = customerRepository.findByEmailAndStatusNot(customerCreationRequest.getEmail(), Status.DELETE);
		if (c != null) {
			throw new AlreadyExitException("Email already Exits !!");
		}

		Customer customer = new Customer();
		customer.setFullName(customerCreationRequest.getFullName());
		customer.setEmail(customerCreationRequest.getEmail());
		customer.setGender(customerCreationRequest.getGender());
		customer.setPhoneNo(customerCreationRequest.getPhoneNo());
		customer.setStatus(Status.ACTIVE);
		customer.setUsername(customerCreationRequest.getUsername());
		customer.setCreatedDate(new Date());

		LOG.debug("Customer Adding..");
		Customer savedCustomer = customerRepository.save(customer);
		LOG.debug("Customer Added");
		if (savedCustomer != null) {

			Login login = new Login();
			login.setLoginStatus(LoginStatus.LOGOUT);
			try {
				login.setPassword(Md5Hashing.getPw(customerCreationRequest.getPassword()));
				login.setCreatedDate(new Date());
				login.setEmail(customerCreationRequest.getEmail());
				login.setUsername(customerCreationRequest.getUsername());
				login.setCustomer(savedCustomer);
				login.setLoginType(LoginType.CUSTOMER);
				login.setStatus(Status.BLOCKED);
				loginService.saveLogin(login);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			TokenGenerator tg = new TokenGenerator();
			String token = tg.generateToken(login.getUsername());

			Verification verification = verificationRepository
					.findVerificationByEmailAndStatusNot(customerCreationRequest.getEmail(), VerificationStatus.EXPIRE);

			if (verification == null) {
				Verification verifiy = new Verification();
				verifiy.setEmail(login.getEmail());
				verifiy.setCreatedDate(new Date());
				verifiy.setExpeireDate(DateUtil.getTokenExpireDate(new Date()));
				verifiy.setToken(token);
				verifiy.setStatus(VerificationStatus.ACTIVE);
				EmailUtility.sendVerification(customerCreationRequest.getEmail(), token);
				verificationService.saveVerification(verifiy);
			}

			LOG.debug("Added.");

			List<CustomerAddressCreationRequest> address = customerCreationRequest.getAddress();
			if (address != null) {
				for (CustomerAddressCreationRequest add : address) {
					Address addresses = new Address();
					addresses.setDistrict(add.getDistrict());
					addresses.setZone(add.getZone());
					addresses.setVdc(add.getVdc());
					addresses.setWardNo(add.getWardNo());
					addresses.setHomeNo(add.getHomeNo());
					addresses.setWardName(add.getWardName());
					addresses.setCustomer(savedCustomer);

					addressRepository.save(addresses);
					LOG.debug("Address Added");
				}
			}

		}

		return customer;
	}

	/**
	 * @param id
	 */
	public void deleteCustomer(Long id) {
		LOG.debug("Deleting Customer..");
		Customer c = customerRepository.findCustomerById(id);
		if (c == null) {
			throw new NotFoundException("Customer Not found !!");

		}

		Login l = loginRepository.findLoginByEmailAndStatusNot(c.getEmail(), Status.DELETE);
		if (l == null) {
			throw new NotFoundException("Customer Not found !!");
		}
		l.setStatus(Status.DELETE);
		c.setStatus(Status.DELETE);
		LOG.debug("Customer Deleted");
		customerRepository.save(c);
	}

	/**
	 * @param editRequest
	 */
	@Transactional
	public Customer editCustomer(CustomerEditRequest editRequest) {
		LOG.debug("Request for Customer edit");
		if (editRequest.getId() == null) {
			throw new RequiredException("User id is needed");

		}

		Customer customer = customerRepository.findCustomerById(editRequest.getId());
		if (customer == null) {
			throw new NotFoundException("User not foud");
		}

		if (editRequest.getEmail() != null) {
			emailDuplication(editRequest.getEmail(), customer);
		}

		if (editRequest.getUsername() != null) {
			usernameDuplication(editRequest.getUsername(), customer);
		}

		if (editRequest.getGender() != null) {
			customer.setGender(editRequest.getGender());
		}
		if (editRequest.getEmail() != null) {
			customer.setFullName(editRequest.getEmail());
		}

		if (editRequest.getPhoneNo() != null) {
			customer.setPhoneNo(editRequest.getPhoneNo());
		}

		if (editRequest.getUsername() != null) {
			customer.setUsername(editRequest.getUsername());
		}

		if (editRequest.getFullName() != null) {
			customer.setFullName(editRequest.getFullName());
		}

		if (editRequest.getAddress() != null) {
			List<AddressEditRequest> addressEditRequests = editRequest.getAddress();
			for (AddressEditRequest address : addressEditRequests) {

				Address add = null;
				if (address.getId() == null) {
					add = new Address();
				}

				else {
					add = addressRepository.findAddressById(address.getId());
				}

				if (null != address.getDistrict()) {
					add.setDistrict(address.getDistrict());
				}
				if (null != address.getZone()) {
					add.setZone(address.getZone());
				}
				if (null != address.getVdc()) {
					add.setVdc(address.getVdc());
				}
				if (null != address.getWardNo()) {
					add.setWardNo(address.getWardNo());
				}

				if (null != address.getWardName()) {
					add.setWardName(address.getWardName());
				}

				if (null != address.getHomeNo()) {
					add.setHomeNo(address.getHomeNo());
				}

				add.setCustomer(customer);
				addressRepository.save(add);
				LOG.debug("Added address.");

			}
		}

		customer.setModifyDate(new Date());
		return customer;
	}

	private void emailDuplication(String email, Customer customer) {
		LOG.debug("Check for Email dublication");

		Customer c = customerRepository.findByEmailAndStatusNot(email, Status.DELETE);
		if (c != null && customer.getId().equals(c.getId())) {

			throw new AlreadyExitException("Email Already Exit");

		}
	}

	private void usernameDuplication(String username, Customer customer) {
		LOG.debug("Check for Username dublication");
		Customer c = customerRepository.findByUsernameAndStatusNot(username, Status.DELETE);
		if (c != null && customer.getId().equals(c.getId())) {

			throw new AlreadyExitException("Username Already Exit");

		}

	}

	/**
	 * @param customerId
	 * @param passwordEditRequest
	 */
	@Transactional
	public void changePassword(Long customerId, PasswordEditRequest passwordEditRequest) {
		LOG.debug("Request Acccepted to change password");
		if (!passwordEditRequest.getNewPassword().equals(passwordEditRequest.getConfirmNewPassword())) {
			throw new ValidationException("New password and confrom password doesnt match");

		}

		Login login = loginRepository.findByUsername(passwordEditRequest.getUsername());

		try {
			if (login == null) {
				throw new ValidationException("Username not found");

			}
			if (!customerId.equals(login.getCustomer().getId())) {
				throw new ValidationException("You are not authorized");
			}

			if (!Md5Hashing.getPw((passwordEditRequest.getOldPassword()))
					.equals(Md5Hashing.getPw(login.getPassword()))) {
				throw new ValidationException("Old Password not match");
			}

			login.setPassword(Md5Hashing.getPw(passwordEditRequest.getNewPassword()));
			loginRepository.save(login);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		LOG.debug("Password Changed");
	}

	/**
	 * @param customerId
	 * @return
	 */
	public CustomerResponceDto getCustomer(Long customerId) {
		LOG.debug("Request to get customer");
		Customer customer = customerRepository.findByIdAndStatusNot(customerId, Status.DELETE);
		if (customer == null) {
			throw new NotFoundException("Customer Not found");
		}
		CustomerResponceDto customerResponceDto = new CustomerResponceDto();
		customerResponceDto.setFullName(customer.getFullName());
		customerResponceDto.setGender(customer.getGender());
		customerResponceDto.setEmail(customer.getEmail());
		customerResponceDto.setUsername(customer.getUsername());
		customerResponceDto.setPhoneNo(customer.getPhoneNo());

		List<AddressResponceDto> adddresss = new ArrayList<>();
		List<Address> add = customer.getAddress();
		if (add != null) {
			add.stream().forEach(u -> {
				AddressResponceDto dd = new AddressResponceDto();
				dd.setId(u.getId());
				dd.setDistrict(u.getDistrict());
				dd.setZone(u.getZone());
				dd.setVdc(u.getVdc());
				dd.setWardName(u.getWardName());
				dd.setWardNo(u.getWardNo());
				dd.setHomeNo(u.getHomeNo());
				adddresss.add(dd);
			});
		}
		customerResponceDto.setAddress(adddresss);
		LOG.debug("Customer Obtain");
		return customerResponceDto;
	}

	/**
	 * @return
	 */
	public List<CustomerDto> listAllCustomer() {
		LOG.debug("Request to get All customer");
		List<Customer> customer = customerRepository.findAllCustomerByStatusNot(Status.DELETE);
		List<CustomerDto> customers = new ArrayList<>();
		if (customer == null) {
			throw new NotFoundException("Customer not found");
		}
		customer.stream().forEach(u -> {
			CustomerDto customerDto = new CustomerDto();
			customerDto.setId(u.getId());
			customerDto.setFullName(u.getFullName());
			customerDto.setEmail(u.getEmail());
			customerDto.setGender(u.getGender());
			customerDto.setUsername(u.getUsername());
			customerDto.setPhoneNo(u.getPhoneNo());
			List<AddressDto> adddresss = new ArrayList<>();
			List<Address> add = u.getAddress();
			if (add != null) {
				add.stream().forEach(a -> {
					AddressDto dd = new AddressDto();
					dd.setId(a.getId());
					dd.setDistrict(a.getDistrict());
					dd.setZone(a.getZone());
					dd.setVdc(a.getVdc());
					dd.setWardName(a.getWardName());
					dd.setWardNo(a.getWardNo());
					dd.setHomeNo(a.getHomeNo());
					adddresss.add(dd);
				});
			}
			customerDto.setAddress(adddresss);
			customers.add(customerDto);

		});
		LOG.debug("All customer Obtain");
		return customers;
	}

	/**
	 * @param token
	 */
	public void getVerify(String token) {

		Verification v = verificationRepository.findVerificationByTokenAndStatusNot(token, VerificationStatus.EXPIRE);
		if (v == null) {
			throw new ExpireException("The session in invallied");
		}

		if (DateUtil.compareDate(v.getCreatedDate(), v.getExpeireDate()) == false) {
			v.setStatus(VerificationStatus.EXPIRE);
			verificationService.saveVerification(v);
			throw new ExpireException("Sorry !! Token is expired");
		}

		Login l = loginRepository.findLoginByEmailAndStatus(v.getEmail(), Status.BLOCKED);
		if (l != null) {
			l.setStatus(Status.ACTIVE);
			v.setStatus(VerificationStatus.EXPIRE);
			verificationService.saveVerification(v);
			loginRepository.save(l);
		}
	}

}
