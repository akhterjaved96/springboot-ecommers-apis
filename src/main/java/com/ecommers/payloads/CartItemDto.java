package com.ecommers.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CartItemDto {

    private Integer id;
    private ProductDto product;
    private Integer quantity;

    @JsonProperty("total_price")
    public Double getTotalPrice()
    {
        return quantity*this.product.getPrice();
    }


}
