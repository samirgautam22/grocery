package com.project.grocery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.grocery.dto.CustomerDto;
import com.project.grocery.request.CustomerCreationRequest;
import com.project.grocery.request.CustomerEditRequest;
import com.project.grocery.request.PasswordEditRequest;
import com.project.grocery.responce.CustomerResponceDto;
import com.project.grocery.service.CustomerService;

import io.swagger.annotations.ApiOperation;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 5, 2018
 * 
 */
@RestController
@RequestMapping(value="api/v1/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);
	@ApiOperation(value="Post customer",notes="Api to post customer")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> createCustomer(@Validated @RequestBody CustomerCreationRequest 
			customerCreationRequest){
		   LOG.debug("Customer Creation request");
		   customerService.saveCustomer(customerCreationRequest);
		return new ResponseEntity<Object>("Registered Sucessfull !Check Email to verify your account.",HttpStatus.CREATED);
	}
	@ApiOperation(value="delete customer",notes="Api to delete customer")
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteCustomer(@PathVariable ("id") Long id){
		  LOG.debug("Customer Delete Request");
		customerService.deleteCustomer(id);
		return new ResponseEntity<Object>("Customer Deleted",HttpStatus.OK);
	}
	@ApiOperation(value="Edit customer",notes="Api to edit customer")
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Object> editCustomer(@Validated @RequestBody CustomerEditRequest editRequest ){
		 LOG.debug("Customer Edit Request");
		 customerService.editCustomer(editRequest);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	@ApiOperation(value="change password",notes="Api to change password")
	@RequestMapping(value="/changePassword",method=RequestMethod.PUT)
	public ResponseEntity<Object> changePassword(@RequestHeader Long customerId,
			@RequestBody PasswordEditRequest passwordEditRequest){
		LOG.debug("Request accepted to change password.");
		customerService.changePassword(customerId,passwordEditRequest);
		return new ResponseEntity<Object>("Password changed",HttpStatus.OK);
		
	}
	@ApiOperation(value="Get customer",notes="Api to get customer")
	@RequestMapping(value="/customer/{customerId}",method=RequestMethod.GET)
	public ResponseEntity<Object> getCustomer(@PathVariable Long customerId
			){
		LOG.debug("Request accepted to get customer.");
		CustomerResponceDto customerResponce= customerService.getCustomer(customerId);
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.put("customer", customerResponce);
		return new ResponseEntity<Object>(response,HttpStatus.OK);
		
	}
	@ApiOperation(value="List Customer",notes="Api to List Customer")
	@RequestMapping(value="/listAll",method=RequestMethod.GET)
	public ResponseEntity<Object> listAllCustomer(){
		List<CustomerDto> customer=customerService.listAllCustomer();
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.put("customers", customer);
		return new ResponseEntity<Object>(response,HttpStatus.OK);
	}
	@ApiOperation(value="verifivation",notes="Api to get verification")
	@RequestMapping(value="/{value}",method=RequestMethod.GET)
	public ResponseEntity<Object> getVerification(@PathVariable ("value") String token){
		LOG.debug("Request To verify Account");
		customerService.getVerify(token);
		return new ResponseEntity<Object>("Sucessfully Verified Login to continue",HttpStatus.OK);
	}
	
	

}
