package com.ecommers.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommers.entities.Category;
import com.ecommers.exceptions.ResourceNotFoundException;
import com.ecommers.payloads.CategoryDto;
import com.ecommers.repository.CategoryRepository;
import com.ecommers.service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addCat = this.categoryRepository.save(cat);	
		return this.modelMapper.map(addCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId) {
		Category cat = this.categoryRepository.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", catId));
		
		cat.setTitle(categoryDto.getTitle());
		cat.setDescription(categoryDto.getDescription());
		
		Category updateCat = this.categoryRepository.save(cat);
		return this.modelMapper.map(updateCat, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategory(Integer catId) {
		Category cat = this.categoryRepository.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", catId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> findAll = this.categoryRepository.findAll();
		List<CategoryDto> collect = findAll.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public void deleteCategory(Integer catId) {
		Category cat = this.categoryRepository.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", catId));
		this.categoryRepository.delete(cat);
		
	}

}

