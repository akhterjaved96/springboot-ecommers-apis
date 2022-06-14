package com.ecommers.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommers.entities.Cart;
import com.ecommers.entities.User;

public interface CartRepo extends JpaRepository<Cart,Integer> {

    Cart findByUser(User user);
}