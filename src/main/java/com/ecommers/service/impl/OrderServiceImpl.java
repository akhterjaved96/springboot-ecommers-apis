package com.ecommers.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommers.entities.Cart;
import com.ecommers.entities.Order;
import com.ecommers.entities.OrderItem;
import com.ecommers.entities.User;
import com.ecommers.exceptions.ResourceNotFoundException;
import com.ecommers.exceptions.ResourceNotFoundException1;
import com.ecommers.payloads.OrderDto;
import com.ecommers.payloads.OrderRequest;
import com.ecommers.repository.CartRepo;
import com.ecommers.repository.OrderRepo;
import com.ecommers.repository.UserRepository;
import com.ecommers.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;
	
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private OrderRepo orderRepo;
    
    @Autowired
	private ModelMapper modelMapper;

    @Override
    public void createOrder(OrderRequest request,String username) {

        //actual order create

        Cart cart = this.cartRepo.findById(request.getCartId()).orElseThrow(() -> new ResourceNotFoundException1("Cart", "cart id ", request.getCartId() + ""));

        Order order = new Order();
        Set<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> new OrderItem(cartItem.getProduct(), cartItem.getQuantity(), order)).collect(Collectors.toSet());

        order.setItems(orderItems);
        order.setBillingAddress(request.getAddress());
        order.setOrderCreated(new Date());
        order.setOrderStatus("PENDING");
        order.setPaymentStatus("NOT PAID");
        order.setOrderDelivered(null);

        final double[] totalPrice = {0};
        cart.getItems().forEach(cartItem -> {
            totalPrice[0] = totalPrice[0] + cartItem.getTotalPrice();
        });

        order.setTotalAmount(totalPrice[0]);
        //calculate the total price:
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException1("User ", "user id", username + ""));


        order.setUser(user);

        //how to save order to db
        this.orderRepo.save(order);

        cart.getItems().clear();

        this.cartRepo.save(cart);

    }
    
    //get orders
    @Override
    public List<OrderDto> getOrders() {
        List<Order> all = this.orderRepo.findAll();
        //System.out.println(all.get(0).getItems().isEmpty());
        List<OrderDto> data = all.stream().map((order -> this.modelMapper.map(order, OrderDto.class))).collect(Collectors.toList());
        return data;
    }
    
    //delete order
    @Override
	public void delete(Integer orderId) {
		Order order = this.orderRepo.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order", "order id", orderId));
    	this.orderRepo.delete(order);
	}

	@Override
	public OrderDto getOrderById(Integer id) {
		Order order = this.orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "order id", id));
		return this.modelMapper.map(order,OrderDto.class);
		
	}

}
