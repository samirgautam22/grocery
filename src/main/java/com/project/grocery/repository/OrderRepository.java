package com.project.grocery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.grocery.model.Order;
import com.project.grocery.model.Store;
import com.project.grocery.util.OrderStatus;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 13, 2018
 * 
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

	/**
	 * @param storeId
	 * @return
	 */
	List<Order> findAllOrderByStoreId(Long storeId);




	/**
	 * @param orderId
	 * @param delete
	 * @return
	 */
	Order findOrderByIdAndOrderStatusNot(Long orderId, OrderStatus delete);



	/**
	 * @param delivered
	 * @return
	 */
	List<Order> findOrderByOrderStatusNot(OrderStatus delivered);



	/**
	 * @param store
	 * @param avialiable
	 * @return
	 */
	List<Order> findOrderByStoreAndOrderStatus(Store store, OrderStatus avialiable);



}
