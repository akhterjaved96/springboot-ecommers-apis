package com.ecommers.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommers.payloads.ApiResponse;
import com.ecommers.payloads.CategoryDto;
import com.ecommers.service.CategoryService;


@RestController
@RequestMapping("/api/v1/category/")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	    //create category
        @PreAuthorize("hasRole('ADMIN')")
		@PostMapping("/")
		public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
			CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
			return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
			
		}
    
        //get all category
  		@PreAuthorize("hasRole('NORMAL')")
  		@GetMapping("/")
  		public ResponseEntity<List<CategoryDto>> getAllCategory(){
  			List<CategoryDto> allCategory = this.categoryService.getAllCategory();
  			return ResponseEntity.ok(allCategory);
  		}
		
		//update category
		@PutMapping("/{catId}")
		public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId){
			CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, catId);
			return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
		}
		
		//get single category
		@GetMapping("/{catId}")
		public ResponseEntity<CategoryDto > getCategory(@PathVariable Integer catId){
			CategoryDto category = this.categoryService.getCategory(catId);
			return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
		}

		//delete category
		@DeleteMapping("/{catId}")
		public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
			this.categoryService.deleteCategory(catId);
			return new ResponseEntity<>(new ApiResponse("Category is Successfully Deleted !!", true), HttpStatus.OK);
		}


}