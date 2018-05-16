package com.project.grocery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.project.grocery.request.OrderCreatationRequest;
import com.project.grocery.responce.OrderResponceDto;
import com.project.grocery.service.OrderService;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 13, 2018
 * 
 */
@RestController
@RequestMapping(value="/api/v1/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> saveOrder(@RequestHeader Long customerId,@RequestHeader Long storeId,
			@RequestBody OrderCreatationRequest orderRequest ){
		LOG.debug("Request Accepted To order item");
		orderService.saveOrder(customerId,storeId,orderRequest);
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	
	public ResponseEntity<Object> listAllOrder(){
		LOG.debug("Request to list all users");
		List<OrderResponceDto> responceDto= orderService.listAllOrder();
		Map<Object, Object> responce=new HashMap<>();
		responce.put("order", responceDto);
		return new ResponseEntity<Object>(responce,HttpStatus.OK);
	}

}
