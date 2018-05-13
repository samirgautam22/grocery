package com.project.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.grocery.model.OrderName;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 13, 2018
 * 
 */
public interface OrderNameRepository extends JpaRepository<OrderName,Long> {

	/**
	 * @param id
	 * @return
	 */
	OrderName findAllOrderNameById(Long id);

}
