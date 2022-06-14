package com.ecommers.service;

import java.util.List;
import com.ecommers.payloads.CategoryDto;

public interface CategoryService {
	
	//create Category
	CategoryDto createCategory(CategoryDto categoryDto);
			
	//update Category
	CategoryDto updateCategory(CategoryDto categoryDto, Integer catId);
		
	//get single Category
	CategoryDto getCategory(Integer catId);
			
	//get all Category
	List<CategoryDto> getAllCategory();
			
	//delete Category
	void deleteCategory(Integer catId); 

}