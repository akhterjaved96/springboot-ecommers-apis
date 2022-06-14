package com.ecommers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommers.payloads.ApiResponse;
import com.ecommers.payloads.OrderDto;
import com.ecommers.payloads.OrderRequest;
import com.ecommers.payloads.ProductDto;
import com.ecommers.service.OrderService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //for create order
    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(
            @RequestBody OrderRequest orderRequest,
            Principal principal
    ) {
        //let suppose order is create success
        String username = principal.getName();
        this.orderService.createOrder(orderRequest,username);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Order is created successfully !!");
       // apiResponse.setStatus(HttpStatus.OK);
        return ResponseEntity.ok(apiResponse);
    }

    // get orders
    @GetMapping("/order")
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = this.orderService.getOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }


    // delete orders
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer orderId) {
       	this.orderService.delete(orderId);
      	return new ResponseEntity<>(new ApiResponse("Order is Successfully Deleted !!", true), HttpStatus.OK);
     }
    
    //single order:by id
    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Integer orderId) {
    	OrderDto orderById = this.orderService.getOrderById(orderId);
		return new ResponseEntity<>(orderById, HttpStatus.OK);
     }


}
