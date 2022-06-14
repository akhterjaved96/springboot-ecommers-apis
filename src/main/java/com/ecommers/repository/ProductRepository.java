package com.ecommers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommers.entities.Category;
import com.ecommers.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	List<Product> findByCategory(Category category);
	
	List<Product> findByIsLiveFalse();

    List<Product> findByInStockFalse();
    
    @Query("SELECT p from  Product p where p.name like :xyz ")
    List<Product> searchProducts(@Param("xyz") String keywords);

}
