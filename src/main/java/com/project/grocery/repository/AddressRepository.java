package com.project.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.grocery.model.Address;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 6, 2018
 * 
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {



}
