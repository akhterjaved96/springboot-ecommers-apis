package com.ecommers.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Boolean isLive = false;

    private Boolean inStock = true;

    private Integer rating;

    private String imageName;

    @ManyToOne
    @JsonIgnore
    private Category category;

//  @OneToOne(mappedBy = "product")
//  private CartItem cartItem;
 
   
}