package com.ecommers.payloads;

import lombok.Data;

@Data
public class OrderRequest {

    private Integer cartId;

    private  String address;

}
