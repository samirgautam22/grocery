package com.project.grocery.controller;

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

import com.project.grocery.request.CustomerCreationRequest;
import com.project.grocery.request.CustomerEditRequest;
import com.project.grocery.service.CustomerService;

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
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> createCustomer(@Validated @RequestBody CustomerCreationRequest 
			customerCreationRequest,@RequestHeader Long userId){
		   LOG.debug("Customer Creation request");
		   customerService.saveCustomer(userId,customerCreationRequest);
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteCustomer(@PathVariable ("id") long id){
		  LOG.debug("Customer Delete Request");
		customerService.deleteCustomer(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	public ResponseEntity<Object> editCustomer(@Validated @RequestBody CustomerEditRequest editRequest ){
		 LOG.debug("Customer Edit Request");
		 customerService.editCustomer(editRequest);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
