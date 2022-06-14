package com.ecommers.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommers.payloads.ApiResponse;
import com.ecommers.payloads.ProductDto;
import com.ecommers.service.FileService;
import com.ecommers.service.ProductService;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FileService fileService;
			
	@Value("${product.images}")
	private String path;
	
	//create product
	@PostMapping("/category/{catId}/product")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, @PathVariable Integer catId){
		ProductDto createProduct = this.productService.createProduct(productDto, catId);
		return new ResponseEntity<ProductDto>(createProduct, HttpStatus.CREATED);
	}
	
	//update product
	@PutMapping("/product/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable Integer productId){
		ProductDto updateProduct = this.productService.updateProduct(productDto, productId);
		return new ResponseEntity<ProductDto>(updateProduct, HttpStatus.CREATED);
	}
	
	//get all product
	@GetMapping("/product")
	public ResponseEntity<List<ProductDto>> getAllProduct(){
		List<ProductDto> allProduct = this.productService.getAllProduct();
		return ResponseEntity.ok(allProduct);
		
	}
	
	//get single product
	@GetMapping("/product/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable Integer productId){
		ProductDto getProduct = this.productService.getProduct(productId);
		return new ResponseEntity<>(getProduct, HttpStatus.OK);
	}
	
	//delete product
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer productId){
		this.productService.deleteProduct(productId);
		return new ResponseEntity<>(new ApiResponse("Product is Successfully Deleted !!", true), HttpStatus.OK);
	}
	
	//get single product by category
	@GetMapping("/category/{catId}/product/")
	public ResponseEntity<List<ProductDto>> getProductBycategory(@PathVariable Integer catId){
		List<ProductDto> productByCategory = this.productService.getProductByCategory(catId);
		return new ResponseEntity<>(productByCategory, HttpStatus.OK);
	}
	
	// get product not-live
	@GetMapping("/product/not-live")
    public ResponseEntity<List<ProductDto>> getAllProductsNotLive() {
        return new ResponseEntity<>(this.productService.getProductNotLive(), HttpStatus.OK);
    }
	
	// get product out of stock
	@GetMapping("/product/out-of-stock")
	public ResponseEntity<List<ProductDto>> getAllProductsOutOfStock() {
	    return new ResponseEntity<>(this.productService.getProductOutOfStock(), HttpStatus.OK);
	}
	
	@GetMapping("/product/search/{keywords}")
    public ResponseEntity<List<ProductDto> > searchProducts(@PathVariable String keywords) {
        List<ProductDto> productDtos = this.productService.searchProduct(keywords);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
	
	//post image upload
	@PostMapping("/product/image/upload/{productId}")
	public ResponseEntity <ProductDto> uploadProductImage(@RequestParam("image") MultipartFile image, @PathVariable Integer productId) throws Exception{
		
		ProductDto productDto = this.productService.getProduct(productId);
		
		String fileName = this.fileService.uploadImage(path, image);
		productDto.setImageName(fileName);
		ProductDto updateProduct = this.productService.updateProduct(productDto, productId);
		return new ResponseEntity<ProductDto>(updateProduct, HttpStatus.OK);
	} 
	
	//method to serve file
	@GetMapping(value="/product/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	@DeleteMapping(value = "/product/image/{productId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Integer productId) throws IOException{
    	this.productService.deleteProductImage(productId, path);
    	ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("image is deleted successfully");
        apiResponse.setSuccess(true);
        //apiResponse.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
