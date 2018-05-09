package com.project.grocery.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.grocery.exception.AlreadyExitException;
import com.project.grocery.exception.NotFoundException;
import com.project.grocery.exception.RequiredException;
import com.project.grocery.model.Address;
import com.project.grocery.model.Customer;
import com.project.grocery.model.Login;
import com.project.grocery.model.User;
import com.project.grocery.repository.AddressRepository;
import com.project.grocery.repository.CustomerRepository;
import com.project.grocery.repository.LoginRepository;
import com.project.grocery.request.AddressEditRequest;
import com.project.grocery.request.CustomerAddressCreationRequest;
import com.project.grocery.request.CustomerCreationRequest;
import com.project.grocery.request.CustomerEditRequest;
import com.project.grocery.util.LoginStatus;
import com.project.grocery.util.LoginType;
import com.project.grocery.util.Md5Hashing;
import com.project.grocery.util.Status;

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

	/**
	 * @param userId
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

			
			try {
				Login login = new Login();
				login.setLoginStatus(LoginStatus.LOGOUT);
				login.setPassword(Md5Hashing.getPw(customerCreationRequest.getPassword()));
				login.setCreatedDate(new Date());
				login.setUsername(customerCreationRequest.getUsername());
				login.setCustomer(savedCustomer);
				login.setLoginType(LoginType.CUSTOMER);
				login.setStatus(Status.ACTIVE);
				loginService.saveLogin(login);
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
						LOG.debug("Address Add");
					}
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			

		}

		return customer;
	}

	/**
	 * @param id
	 */
	public void deleteCustomer(long id) {
		LOG.debug("Deleting Customer..");
		Customer c=customerRepository.findCustomerById(id);
		if(c==null) {
			throw new NotFoundException("Customer Not found !!");
			
		}
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
		if(editRequest==null) {
			throw new RequiredException("User id is needed");          
			
			
		}
		
		Customer customer=customerRepository.findCustomerById(editRequest.getId());
		if(customer==null) {
			throw new NotFoundException("User not foud");
		}
		
		if(editRequest.getEmail()!=null) {
			emailDuplication(editRequest.getEmail(), customer);
		}
		
		if(editRequest.getUsername()!=null) {
			usernameDuplication(editRequest.getUsername(), customer);	
		}
		
		if(editRequest.getGender()!=null) {
			customer.setGender(editRequest.getGender());	
		}
		if(editRequest.getEmail()!=null) {
			customer.setFullName(editRequest.getEmail());	
		}
		
		if(editRequest.getPhoneNo()!=null) {
			customer.setPhoneNo(editRequest.getPhoneNo());	
		}
		
		
		if(editRequest.getUsername()!=null) {
			customer.setUsername(editRequest.getUsername());	
		}
		
		if (editRequest.getFullName() != null) {
			customer.setFullName(editRequest.getFullName());
		}
		
		
		if(editRequest.getAddress()!=null) {
			List<AddressEditRequest> addressEditRequests=editRequest.getAddress();
			for (AddressEditRequest address : addressEditRequests) {
				

				Address add = null;
				if (address.getId() == null) {
					add = new Address();
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
		customer.setModifyBy(new User(editRequest.getId()));
		return customer;
	}
	
	
	private void emailDuplication(String email, Customer customer) {
		LOG.debug("Check for Email dublication");

		Customer c = customerRepository.findByEmailAndStatusNot(email, Status.DELETE);
		if (c!= null && customer.getId().equals( c.getId())) {

			throw new AlreadyExitException("Email Already Exit");

		}
	}
		
		private void usernameDuplication(String username, Customer customer) {
			LOG.debug("Check for Username dublication");
			Customer c = customerRepository.findByUsernameAndStatusNot(username, Status.DELETE);
			if (c!= null && customer.getId().equals( c.getId())) {

				throw new AlreadyExitException("Username Already Exit");

			}
		
	}

}
