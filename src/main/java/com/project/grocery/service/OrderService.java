package com.project.grocery.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.grocery.exception.NotFoundException;
import com.project.grocery.model.Customer;
import com.project.grocery.model.Order;
import com.project.grocery.model.Store;
import com.project.grocery.repository.CustomerRepository;
import com.project.grocery.repository.OrderRepository;
import com.project.grocery.request.OrderCreatationRequest;
import com.project.grocery.responce.OrderResponceDto;
import com.project.grocery.util.Status;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 13, 2018
 * 
 */
@Service
public class OrderService {

	private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	OrderRepository orderRepository;

	/**
	 * @param customerId
	 * @param orderRequest
	 */
	public Order saveOrder(Long customerId,Long storeId,OrderCreatationRequest orderRequest) {
		LOG.debug("Request Accepted to Save order ");
	
		Customer customer = customerRepository.findCustomerByIdAndStatusNot(customerId, Status.DELETE);
		if (customer == null) {
			throw new NotFoundException("Customer Not Found");
		}
		
		Order order = new Order();
		order.setOrderDate(new Date());
		order.setCustomer(customer);
		order.setPrice(orderRequest.getPrice());
		order.setItem(orderRequest.getItem());
		order.setOrderName(orderRequest.getOrderName());
		order.setTotalPrice(orderRequest.getPrice()*orderRequest.getItem());
		order.setCustomer(new Customer(customerId));
		order.setStore(new Store(storeId));
		orderRepository.save(order);
		
		LOG.debug("The customer order has been set");

		return order;
	}

	/**
	 * @return 
	 * 
	 */
	public List<OrderResponceDto> listAllOrder() {
		LOG.debug("Request Accepted to List All order");
		return null;
	}

}
