package com.ecommers.service;

import com.ecommers.payloads.CartDto;
import com.ecommers.payloads.CartItemRequest;

public interface CartService {

    CartDto addItemToCart(CartItemRequest cartItemRequest,String username);
    
    CartDto getCart(String username);
    
    CartDto removeCartItem(String username,Integer productId);
}
