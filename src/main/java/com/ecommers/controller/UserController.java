package com.ecommers.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ecommers.payloads.RoleDto;
import com.ecommers.payloads.UserDto;
import com.ecommers.service.FileService;
import com.ecommers.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
			
	@Value("${user.profiles}")
	private String path;
	
	//create user
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createProduct = this.userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createProduct, HttpStatus.CREATED);
			
	}
	
	//get all users
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/role/{userId}")
    public ResponseEntity<UserDto> updateRoles(@PathVariable Integer userId, @RequestBody List<RoleDto> roles) {
        UserDto userDto = this.userService.updateRole(userId, roles);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
	
	//update user
	@PutMapping("/users/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer userId){
		UserDto updateUser = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updateUser, HttpStatus.CREATED);
	}
	
	//get all user
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUser(){
		List<UserDto> allUser = this.userService.getAllUser();
		return ResponseEntity.ok(allUser);
		
	}
	
	//delete user
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<>(new ApiResponse("User is Successfully Deleted !!", true), HttpStatus.OK);
	}
	
	//get single user
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto> getProductBycategory(@PathVariable Integer userId){
		UserDto user4 = this.userService.getUser(userId);
		return new ResponseEntity<>(user4, HttpStatus.OK);
	}
	
	//search user by name
	@GetMapping("/users/search/{keyword}")
	public ResponseEntity<List<UserDto>> searchByUser(@PathVariable String keyword){
		List<UserDto> searchName = this.userService.searchUser(keyword);
		return new ResponseEntity<List<UserDto>>(searchName, HttpStatus.OK);
	}
	
	//post image upload
	@PostMapping("/users/image/upload/{userId}")
	public ResponseEntity <UserDto> uploadProductImage(@RequestParam("image") MultipartFile image, @PathVariable Integer userId) throws Exception{
		
		UserDto userDto = this.userService.getUser(userId);
		
		String fileName = this.fileService.uploadImage(path, image);
		userDto.setImageName(fileName);
		UserDto updateUser = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updateUser, HttpStatus.OK);
	} 
	
	//method to serve file
	@GetMapping(value="/users/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	//delete user image
	@DeleteMapping("/users/user-profile/{userId}")
	public ResponseEntity<ApiResponse> deleteUserImage(@PathVariable Integer userId) throws IOException {
		    	this.userService.deleteUserImage(userId, path);
		    	ApiResponse apiResponse = new ApiResponse();
		        apiResponse.setMessage("image is deleted successfully");
		        apiResponse.setSuccess(true);
		        //apiResponse.setStatus(HttpStatus.OK);
		        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

}
