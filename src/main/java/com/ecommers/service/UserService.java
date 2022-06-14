package com.ecommers.service;

import java.io.IOException;
import java.util.List;

import com.ecommers.payloads.RoleDto;
import com.ecommers.payloads.UserDto;

public interface UserService {
	
	//create User
	UserDto createUser(UserDto userDto);
			
	//update User
	UserDto updateUser(UserDto userDto, Integer userId);
						
	//get all User
	List<UserDto> getAllUser();
	
	//get single User
	UserDto getUser(Integer userId);
				
	//delete User
	void deleteUser(Integer userId); 
	
	//create role
	UserDto updateRole(Integer userId, List<RoleDto> roles);
	
	//search user by name
    List<UserDto> searchUser(String keyword);
    
    //user Image delete
    void deleteUserImage(Integer userId,String userPath) throws IOException;

}
