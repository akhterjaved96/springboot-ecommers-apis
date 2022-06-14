package com.ecommers.service;

import java.util.List;

import com.ecommers.payloads.OrderDto;
import com.ecommers.payloads.OrderRequest;

public interface OrderService {

    void createOrder(OrderRequest request,String username);
    
    List<OrderDto> getOrders();
    
    void delete(Integer orderId);
    
    OrderDto getOrderById(Integer id);
    
}
