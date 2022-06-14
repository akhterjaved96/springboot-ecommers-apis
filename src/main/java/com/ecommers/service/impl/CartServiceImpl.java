package com.ecommers.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommers.entities.Cart;
import com.ecommers.entities.CartItem;
import com.ecommers.entities.Product;
import com.ecommers.entities.User;
import com.ecommers.exceptions.ResourceNotFoundException1;
import com.ecommers.payloads.CartDto;
import com.ecommers.payloads.CartItemRequest;
import com.ecommers.repository.CartRepo;
import com.ecommers.repository.ProductRepository;
import com.ecommers.repository.UserRepository;
import com.ecommers.service.CartService;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addItemToCart(CartItemRequest cartItemRequest, String username) {

        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException1("user", "username", username));
        Product product = this.productRepository.findById(cartItemRequest.getProductId()).orElseThrow(() -> new ResourceNotFoundException1("product ", "product id ", cartItemRequest.getProductId() + ""));
       
        CartItem cartItem = new CartItem();
        
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemRequest.getQuantity());
        
        Cart cart = user.getCart();
        
        if (cart == null) {
            cart = new Cart();
        }
        
        cartItem.setCart(cart);
        cart.setUser(user);
        
        Set<CartItem> items = cart.getItems();
        
        AtomicReference<Boolean> updatedQuantity = new AtomicReference<>(false);
        
        Set<CartItem> newCartItems = items.stream().map((cartItem1 -> {
            if (cartItem1.getProduct().getId() == product.getId()) {
                cartItem1.setQuantity(cartItemRequest.getQuantity());
                updatedQuantity.set(true);
            }
            return cartItem1;
        })).collect(Collectors.toSet());
        
        if (updatedQuantity.get()) {
            cart.setItems(newCartItems);
        } 
        else {
            items.add(cartItem);
        }
        
        Cart updatedCart = this.cartRepo.save(cart);
        System.err.println(updatedCart.getUser());
        
        return this.modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public CartDto getCart(String username) {
    	
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException1("User ", " username ", username));
       
        Cart cart = user.getCart();
        
        return this.modelMapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto removeCartItem(String username, Integer productId) {
    	
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException1("User ", " username ", username));
        Cart cart = this.cartRepo.findByUser(user);
        
        Set<CartItem> items = cart.getItems();
        
        boolean b = items.removeIf((item) -> item.getProduct().getId().equals(productId));
        System.out.println(b);
        
        Cart car1 = this.cartRepo.save(cart);
        
        System.out.println(car1.getItems().size());
        return this.modelMapper.map(car1, CartDto.class);
    }
}
