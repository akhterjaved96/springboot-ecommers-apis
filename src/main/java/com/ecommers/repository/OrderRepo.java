package com.ecommers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommers.entities.Order;

public interface OrderRepo extends JpaRepository<Order,Integer> {
	
}
