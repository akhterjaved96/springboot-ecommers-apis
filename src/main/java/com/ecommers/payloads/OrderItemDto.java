package com.ecommers.payloads;

import lombok.Data;
import com.ecommers.entities.Product;

@Data
public class OrderItemDto {
    private Integer id;


    private Product product;

    private Integer quantity;
}
