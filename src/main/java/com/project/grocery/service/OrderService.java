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
import com.project.grocery.model.OrderName;
import com.project.grocery.repository.CustomerRepository;
import com.project.grocery.repository.OrderNameRepository;
import com.project.grocery.repository.OrderRepository;
import com.project.grocery.request.OrderCreatationRequest;
import com.project.grocery.request.OrderNameCreatation;
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

	@Autowired
	OrderNameRepository orderNameRepository;
	/**
	 * @param customerId
	 * @param orderRequest
	 */
	public Order saveOrder(Long customerId, OrderCreatationRequest orderRequest) {
		LOG.debug("Request Accepted to Save order ");
//		int item=0;
//		double price=0;
//		
		Customer customer = customerRepository.findCustomerByIdAndStatusNot(customerId, Status.DELETE);
		if (customer == null) {
			throw new NotFoundException("Customer Not Found");
		}
		
		Order order = new Order();
		order.setOrderDate(new Date());
		order.setCustomer(customer);
		Order savedOrder = orderRepository.save(order);
		
		if (savedOrder != null) {

			List<OrderNameCreatation> orderNameCreatation = orderRequest.getOrderName();
			LOG.debug("Order Name Requested...");
			
			if (orderNameCreatation != null) {
				for (OrderNameCreatation name : orderNameCreatation) {
					OrderName orderName = new OrderName();
					orderName.setOrder(order);
					orderName.setOrderName(name.getOrderName());
					orderName.setPrice(name.getPrice());
					orderNameRepository.save(orderName);
					LOG.debug("Order Added");
				}
			}
		}
//		
//		OrderName oo=new OrderName();
//		OrderName o=orderNameRepository.findAllOrderNameById(oo.getId());
//		List<>
		return order;
	}

}
