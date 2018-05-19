package com.project.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.grocery.model.Items;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 19, 2018
 * 
 */
@Repository
public interface ItemsRepository  extends JpaRepository<Items, Long>{

}
