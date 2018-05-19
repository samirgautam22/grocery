package com.project.grocery.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.grocery.exception.NotFoundException;
import com.project.grocery.model.Address;
import com.project.grocery.model.Customer;
import com.project.grocery.model.Order;
import com.project.grocery.model.Store;
import com.project.grocery.model.StoreAddress;
import com.project.grocery.repository.CustomerRepository;
import com.project.grocery.repository.OrderRepository;
import com.project.grocery.repository.StoreRepository;
import com.project.grocery.request.OrderCreatationRequest;
import com.project.grocery.responce.AddressResponceDto;
import com.project.grocery.responce.OrderResponceDto;
import com.project.grocery.responce.StoreAddressResponce;
import com.project.grocery.responce.StoreOrderResponce;
import com.project.grocery.util.OrderStatus;
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
	StoreRepository storeRepository;

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
		order.setOrderStatus(OrderStatus.AVIALIABLE);
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
		List<Order> order= orderRepository.findAllOrderAndOrderStatusNot(OrderStatus.AVIALIABLE);
		List<OrderResponceDto> orderResponce=new ArrayList<>();
		order.stream().forEach(u->{
			Customer c=customerRepository.findCustomerById(u.getCustomer().getId());
			if(c==null) {
				throw new NotFoundException("Customer Not found");
			}
			Store s=storeRepository.findStoreById(u.getStore().getId());
			if(s==null) {
				throw new NotFoundException("Store Not found");
			}
			OrderResponceDto responceDto=new OrderResponceDto();
			responceDto.setId(u.getId());
			responceDto.setItem(u.getItem());
			responceDto.setOrderName(u.getOrderName());
			responceDto.setOrderDate(u.getOrderDate());
			responceDto.setPrice(u.getPrice());
			responceDto.setOrderBy(c.getFullName());
			responceDto.getOrderBy();
			
			List<AddressResponceDto> adddresss=new ArrayList<>();
			List<Address> add=c.getAddress();
			if (add != null) {
				add.stream().forEach(a -> {
					AddressResponceDto dd = new AddressResponceDto();
					dd.setId(a.getId());
					dd.setDistrict(a.getDistrict());
					dd.setZone(a.getZone());
					dd.setVdc(a.getVdc());
					dd.setWardName(a.getWardName());
					dd.setWardNo(a.getWardNo());
					dd.setHomeNo(a.getHomeNo());
					adddresss.add(dd);
				});
			}
			
			responceDto.setAddress(adddresss);
			
			List<StoreAddressResponce> adddres=new ArrayList<>();
			List<StoreAddress> adds=s.getStoreAddress();
			if (adds != null) {
				adds.stream().forEach(a -> {
					StoreAddressResponce dd = new StoreAddressResponce();
					dd.setId(a.getId());
					dd.setDistrict(a.getDistrict());
					dd.setZone(a.getZone());
					dd.setVdc(a.getVdc());
					dd.setWardName(a.getWardName());
					dd.setWardNo(a.getWardNo());
					dd.setHomeNo(a.getHomeNo());
					adddres.add(dd);
				});
			}
			responceDto.setStore(adddres);
			orderResponce.add(responceDto);
	
			
		});
		LOG.debug("The order is obtain");
		return orderResponce;
		
	}

	/**
	 * @param storeId
	 * @return
	 */
	public List<StoreOrderResponce> listAllorderByStore(Long storeId) {
		
		List<StoreOrderResponce> orderResponces=new ArrayList<>();
		Store store=storeRepository.findStoreById(storeId);
	
		if(store==null) {
			throw new NotFoundException("Store not found");
		}
		
		
		List<Order> order=orderRepository.findAllOrderByStoreStatusNot(store,OrderStatus.AVIALIABLE);
		
		if(order==null) {
			throw new NotFoundException("Order Not Avaliable in your store");
		}
		
		
		order.stream().forEach(u->{
			StoreOrderResponce orderResponceDto=new StoreOrderResponce();
			System.out.println(u.getOrderName());
			orderResponceDto.setId(u.getId());
			orderResponceDto.setOrderName(u.getOrderName());
			orderResponceDto.setItem(u.getItem());
			orderResponceDto.setOrderDate(u.getOrderDate());
			orderResponceDto.setPrice(u.getPrice());
			orderResponceDto.setTotalPrice(u.getPrice()*u.getItem());
			orderResponceDto.setOrderBy(u.getCustomer().getFullName());
			
			
			List<AddressResponceDto> addressResponceDtos=new ArrayList<>();
			List<Address> addresses=u.getCustomer().getAddress();
			if(addresses!=null) {
				addresses.stream().forEach(a->{
					AddressResponceDto dto=new AddressResponceDto();
					dto.setId(u.getId());
					dto.setZone(a.getZone());
					dto.setDistrict(a.getDistrict());
					dto.setVdc(a.getVdc());
					dto.setWardNo(a.getWardNo());
					dto.setWardName(a.getWardName());
					dto.setHomeNo(a.getHomeNo());
					addressResponceDtos.add(dto);
					
				});
			}
			orderResponceDto.setAddress(addressResponceDtos);
			orderResponces.add(orderResponceDto);
		});
		
		return orderResponces;
	}
	
	
	public List<OrderResponceDto> listAllDeliveredOrder() {
		LOG.debug("Request Accepted to List All order");
		List<Order> order= orderRepository.findAllOrderAndOrderStatusNot(OrderStatus.DELIVERED);
		List<OrderResponceDto> orderResponce=new ArrayList<>();
		order.stream().forEach(u->{
			Customer c=customerRepository.findCustomerById(u.getCustomer().getId());
			if(c==null) {
				throw new NotFoundException("Customer Not found");
			}
			Store s=storeRepository.findStoreById(u.getStore().getId());
			if(s==null) {
				throw new NotFoundException("Store Not found");
			}
			OrderResponceDto responceDto=new OrderResponceDto();
			responceDto.setId(u.getId());
			responceDto.setItem(u.getItem());
			responceDto.setOrderName(u.getOrderName());
			responceDto.setOrderDate(u.getOrderDate());
			responceDto.setPrice(u.getPrice());
			responceDto.setOrderBy(c.getFullName());
			responceDto.getOrderBy();
			
			List<AddressResponceDto> adddresss=new ArrayList<>();
			List<Address> add=c.getAddress();
			if (add != null) {
				add.stream().forEach(a -> {
					AddressResponceDto dd = new AddressResponceDto();
					dd.setId(a.getId());
					dd.setDistrict(a.getDistrict());
					dd.setZone(a.getZone());
					dd.setVdc(a.getVdc());
					dd.setWardName(a.getWardName());
					dd.setWardNo(a.getWardNo());
					dd.setHomeNo(a.getHomeNo());
					adddresss.add(dd);
				});
			}
			
			responceDto.setAddress(adddresss);
			
			List<StoreAddressResponce> adddres=new ArrayList<>();
			List<StoreAddress> adds=s.getStoreAddress();
			if (adds != null) {
				adds.stream().forEach(a -> {
					StoreAddressResponce dd = new StoreAddressResponce();
					dd.setId(a.getId());
					dd.setDistrict(a.getDistrict());
					dd.setZone(a.getZone());
					dd.setVdc(a.getVdc());
					dd.setWardName(a.getWardName());
					dd.setWardNo(a.getWardNo());
					dd.setHomeNo(a.getHomeNo());
					adddres.add(dd);
				});
			}
			responceDto.setStore(adddres);
			orderResponce.add(responceDto);
	
			
		});
		LOG.debug("The order is obtain");
		return orderResponce;
		
	}

	/**
	 * @param orderId
	 */
	@Transactional
	public void deleteOrder(Long orderId) {
		LOG.debug("Request Accepted to Delete order");
		Order order=orderRepository.findOrderByIdStatusNot(orderId,OrderStatus.DELETE);
		if(order==null) {
			throw new NotFoundException("Order not found");
		}
		
		order.setOrderStatus(OrderStatus.DELETE);
		orderRepository.save(order);
		LOG.debug("Order Deleted");
	}

}
