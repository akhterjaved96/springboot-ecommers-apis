package com.ecommers.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommers.entities.Category;
import com.ecommers.entities.Product;
import com.ecommers.exceptions.ResourceNotFoundException;
import com.ecommers.exceptions.ResourceNotFoundException1;
import com.ecommers.payloads.ProductDto;
import com.ecommers.repository.CategoryRepository;
import com.ecommers.repository.ProductRepository;
import com.ecommers.service.FileService;
import com.ecommers.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private FileService fileService;

	@Override
	public ProductDto createProduct(ProductDto productDto, Integer catId) {
		Category category = this.categoryRepository.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", catId));
		Product product = this.modelMapper.map(productDto, Product.class);
		product.setImageName("default");
		product.setCategory(category);
		
		Product newProduct = this.productRepository.save(product);
		return this.modelMapper.map(newProduct, ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto, Integer productId) {
		Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "product Id", productId));
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setIsLive(productDto.getIsLive());
        product.setPrice(productDto.getPrice());
        product.setInStock(productDto.getInStock());
		product.setRating(productDto.getRating());
		product.setImageName(productDto.getImageName());
		
		Product updateProduct = this.productRepository.save(product);
		return this.modelMapper.map(updateProduct, ProductDto.class);
	}

	@Override
	public List<ProductDto> getAllProduct() {
		List<Product> findAll = this.productRepository.findAll();
		List<ProductDto> collect = findAll.stream().map((product)-> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public ProductDto getProduct(Integer productId) {
		Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "product Id", productId));
		return this.modelMapper.map(product, ProductDto.class);
	}

	@Override
	public void deleteProduct(Integer productId) {
		Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "product Id", productId));
		this.productRepository.delete(product);
		
	}

	@Override
	public List<ProductDto> getProductByCategory(Integer catId) {
		Category category = this.categoryRepository.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", catId));
		List<Product> findByCategory = this.productRepository.findByCategory(category);
		List<ProductDto> collect = findByCategory.stream().map((product)-> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<ProductDto> getProductNotLive() {
	      List<Product> products = this.productRepository.findByIsLiveFalse();
	      return products.stream().map((product) -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> getProductOutOfStock() {
		List<Product> products = this.productRepository.findByInStockFalse();
        return products.stream().map((product) -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<ProductDto> searchProduct(String titleKey) {
	    List<Product> products = this.productRepository.searchProducts("%" + titleKey + "%");
	    List<ProductDto> productDtos = products.stream().map(product -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
	    return productDtos;
	}

	@Override
	public void deleteProductImage(Integer productId, String productPath) throws IOException {
		Product product=this.productRepository.findById(productId).orElseThrow(ResourceNotFoundException1::new);
		String productName=product.getImageName();
		 String fullPath=productPath+File.separator+productName;
		 this.fileService.deleteFileIfExists(fullPath);
		 product.setImageName(null);
		 this.productRepository.save(product);
		
	}	

}
