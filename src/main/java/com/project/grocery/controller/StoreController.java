package com.project.grocery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.project.grocery.dto.StoreDto;
import com.project.grocery.request.StoreCreatationRequest;
import com.project.grocery.request.StoreEditRequest;
import com.project.grocery.responce.StoreByAddressDto;
import com.project.grocery.responce.StoreResponceDto;
import com.project.grocery.service.StoreService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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
	@ApiImplicitParams({
		@ApiImplicitParam(name="token",required=true,dataType="String",paramType="header")})
	@ApiOperation(value="Save Store",notes="Api to save store")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> saveStore(@RequestHeader Long userId,
			@RequestBody StoreCreatationRequest storeCreatationRequest){
		LOG.debug("Request to Add store");
		storeService.saveStore(userId,storeCreatationRequest);
		
		return new ResponseEntity<Object>("Store Created",HttpStatus.CREATED);
		
	}
	@ApiImplicitParams({
		@ApiImplicitParam(name="token",required=true,dataType="String",paramType="header")})
	@ApiOperation(value="Delete Store",notes="Api to Delete Store")
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteStore(@RequestHeader Long userId,@PathVariable ("id") Long id){
		  LOG.debug("Store Delete Request");
		  storeService.deleteCustomer(userId,id);
		return new ResponseEntity<Object>("Store Reported",HttpStatus.OK);
	}
	@ApiImplicitParams({
		@ApiImplicitParam(name="token",required=true,dataType="String",paramType="header")})
	@ApiOperation(value="Edit Store",notes="Api to Edit Store")
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Object> editStore(@RequestBody StoreEditRequest storeEditRequest){
		LOG.debug("Request for Store Edit Accepted..");
		storeService.editStore(storeEditRequest);
		LOG.debug("Store Edited");
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	@ApiImplicitParams({
		@ApiImplicitParam(name="token",required=true,dataType="String",paramType="header")})
	@ApiOperation(value="List All Store",notes="Api to List All Store")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Object> listAllStore(){
		LOG.debug("List All Stores");
		List<StoreDto> store=storeService.listAllStores();
		Map<Object,Object> responce=new HashMap<>();
		responce.put("store", store);
		return new ResponseEntity<Object>(responce,HttpStatus.OK);
	}
	@ApiImplicitParams({
		@ApiImplicitParam(name="token",required=true,dataType="String",paramType="header")})
	@ApiOperation(value="Get Store",notes="Api to get Store")
	@RequestMapping(value="/{storeId}",method=RequestMethod.GET)
	public ResponseEntity<Object> getStore(@RequestHeader Long storeId){
		LOG.debug("Request Accepted to get store..");
		StoreResponceDto storeResponce=storeService.getStore(storeId); 
		Map<Object,Object> responce=new HashMap<>();
		responce.put("stores", storeResponce);
		return new ResponseEntity<Object>(responce,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/address",method=RequestMethod.GET)
	public ResponseEntity<Object> getStoreByAddress(@RequestHeader Long customerId){
		LOG.debug("Request Accepted to get store By Address..");
		List<StoreByAddressDto> addressresponce=storeService.getStoreAddress(customerId);
		Map<Object,Object> responce=new HashMap<>();
		responce.put("Store", addressresponce);
		return new ResponseEntity<Object>(responce,HttpStatus.OK);
		
	}

}
