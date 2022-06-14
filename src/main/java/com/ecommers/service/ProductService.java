package com.ecommers.service;

import java.io.IOException;
import java.util.List;

import com.ecommers.payloads.ProductDto;

public interface ProductService {
	
	//create product
	ProductDto createProduct(ProductDto productDto, Integer catId);
	
	//update product
	ProductDto updateProduct(ProductDto productDto, Integer productId);
	
	//get all product
	List<ProductDto> getAllProduct();
	
	//get single product
	ProductDto getProduct(Integer productId);
	
	//delete product
	void  deleteProduct(Integer productId);
	
	//get all product by category
	List<ProductDto> getProductByCategory(Integer catId);
	
	//all products that is not live

    List<ProductDto> getProductNotLive();

    //all product that is out of stock

    List<ProductDto> getProductOutOfStock();
    
    //search product by title
    List<ProductDto> searchProduct(String titleKey);
    
   //Product Image delete
    void deleteProductImage(Integer productId,String productPath) throws IOException;

}
