package com.project.grocery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.grocery.request.StoreCreatationRequest;
import com.project.grocery.request.StoreEditRequest;
import com.project.grocery.service.StoreService;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 12, 2018
 * 
 */
@RestController
@RequestMapping(value="/api/v1/store")
public class StoreController {
	
	private static final Logger LOG = LoggerFactory.getLogger(StoreController.class);
	
	@Autowired
	private StoreService storeService;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> saveStore(@RequestHeader Long userId,
			@RequestBody StoreCreatationRequest storeCreatationRequest){
		LOG.debug("Request to Add store");
		storeService.saveStore(userId,storeCreatationRequest);
		
		return new ResponseEntity<Object>(HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteStore(@RequestHeader Long userId,@PathVariable ("id") Long id){
		  LOG.debug("Store Delete Request");
		  storeService.deleteCustomer(userId,id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Object> editStore(@RequestBody StoreEditRequest storeEditRequest){
		LOG.debug("Request for Store Edit Accepted..");
		storeService.editStore(storeEditRequest);
		LOG.debug("Store Edited");
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
