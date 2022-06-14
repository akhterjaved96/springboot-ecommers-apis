package com.ecommers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommers.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}

