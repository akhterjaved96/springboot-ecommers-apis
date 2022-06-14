package com.ecommers.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ecommers.entities.Product;

import lombok.Data;

@Data
public class CategoryDto {
	
	private Integer id;

	@NotBlank
	@Size(min=4, max = 500, message = "Min size of category title is 4")
    private String title;

	@NotBlank
	@Size(min=10, max = 500, message = "Min size of category description is 10")
    private String description;

	private Set<Product> products = new HashSet<>();

}
