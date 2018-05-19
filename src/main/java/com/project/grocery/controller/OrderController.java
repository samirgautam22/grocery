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

import com.project.grocery.request.OrderCreatationRequest;
import com.project.grocery.responce.OrderResponceDto;
import com.project.grocery.responce.StoreOrderResponce;
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
	
	@RequestMapping(value="/listAllOrderAvaliable",method=RequestMethod.GET)
	public ResponseEntity<Object> listAllOrder(){
		LOG.debug("Request to list all users");
		List<OrderResponceDto> responceDto= orderService.listAllOrder();
		Map<Object, Object> responce=new HashMap<>();
		responce.put("order", responceDto);
		return new ResponseEntity<Object>(responce,HttpStatus.OK);
	}
	
	@RequestMapping(value="/listOrderByStore",method=RequestMethod.GET)
	public ResponseEntity<Object> listOrdereByStore(@RequestHeader Long storeId){
		LOG.debug("Request accepted to List all order by store");	
		List<StoreOrderResponce> responceDto= orderService.listAllorderByStore(storeId);
		Map<Object, Object> responce=new HashMap<>();
		responce.put("order", responceDto);
		return new ResponseEntity<Object>(responce,HttpStatus.OK);
	}
	
	@RequestMapping(value="/listAllOrderDelivered",method=RequestMethod.GET)
	public ResponseEntity<Object> listAllDeliveredOrder(){
		LOG.debug("Request to list all delivered order");
		List<OrderResponceDto> responceDto= orderService.listAllOrder();
		Map<Object, Object> responce=new HashMap<>();
		responce.put("order", responceDto);
		return new ResponseEntity<Object>(responce,HttpStatus.OK);
	}
	@RequestMapping(value="/{orderId}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteOrder(@PathVariable ("orderId") Long orderId){
		LOG.debug("Request Accepted to Delete order");
		orderService.deleteOrder(orderId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
