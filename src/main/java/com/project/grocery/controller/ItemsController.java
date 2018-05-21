package com.project.grocery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.grocery.request.ItemsCreatationRequest;
import com.project.grocery.service.ItemsService;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 19, 2018
 * 
 */
@RestController
@RequestMapping(value = "api/v1/items")
public class ItemsController {

	@Autowired
	ItemsService itemsService;

	private static final Logger LOG = LoggerFactory.getLogger(ItemsController.class);

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> saveItems(@RequestHeader Long userID, @RequestBody ItemsCreatationRequest request) {
		LOG.debug("items cratation by user");
		itemsService.create(userID, request);
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}

}