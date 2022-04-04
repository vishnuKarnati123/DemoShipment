package com.exam.shipement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.shipement.entities.OrderDetails;



@Repository
public interface OrderRepository extends JpaRepository<OrderDetails,Integer>{
	
	

	
}
