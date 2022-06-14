package com.ecommers.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommers.entities.Product;
import com.ecommers.entities.Role;
import com.ecommers.entities.User;
import com.ecommers.exceptions.ResourceNotFoundException;
import com.ecommers.exceptions.ResourceNotFoundException1;
import com.ecommers.payloads.RoleDto;
import com.ecommers.payloads.UserDto;
import com.ecommers.repository.RoleRepo;
import com.ecommers.repository.UserRepository;
import com.ecommers.service.FileService;
import com.ecommers.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;
    
    @Autowired
    private FileService fileService;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        //ROLE_NORMAL
        Role role = this.roleRepo.findByName("ROLE_NORMAL");
        user.getRoles().add(role);
		user.setImageName("default.png");
		user.setDate(new Date());
		User addUser = this.userRepository.save(user);	
		return this.modelMapper.map(addUser, UserDto.class);
	}
	
	@Override
    public UserDto updateRole(Integer userId, List<RoleDto> roles) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException1("User ", "user id ", userId + ""));
        Set<Role> roles1 = user.getRoles();
        roles1.clear();
        roles1.addAll(roles.stream().map(roleDto -> this.modelMapper.map(roleDto,Role.class)).collect(Collectors.toSet()));
        User save = this.userRepository.save(user);
        return this.modelMapper.map(save, UserDto.class);


    }

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user1 = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		user1.setName(userDto.getName());
		user1.setEmail(userDto.getEmail());
		user1.setPassword(userDto.getPassword());
		user1.setImageName(userDto.getImageName());
		user1.setAddress(userDto.getAddress());
		user1.setAbout(userDto.getAbout());
		
		User updateUser = this.userRepository.save(user1);
		return this.modelMapper.map(updateUser, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> findAll = this.userRepository.findAll();
		List<UserDto> collect = findAll.stream().map((user)-> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user2 = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user Id", userId));
		
//      user.getRoles().clear();
//      this.userRepository.save(user);
		
		this.userRepository.delete(user2);
		
	}

	@Override
	public UserDto getUser(Integer userId) {
		User user3 = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user Id", userId));
		return this.modelMapper.map(user3, UserDto.class);
	}
	
	@Override
	public List<UserDto> searchUser(String keyword) {
		List<User> name = this.userRepository.findByName("%"+keyword+"%");
		List<UserDto> collect1 = name.stream().map((user)->this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		return collect1;
	}

	@Override
	public void deleteUserImage(Integer userId, String userPath) throws IOException {
		User user=this.userRepository.findById(userId).orElseThrow(ResourceNotFoundException1::new);
		String userName=user.getImageName();
		 String fullPath=userPath+File.separator+userName;
		 this.fileService.deleteFileIfExists(fullPath);
		 user.setImageName(null);
		 this.userRepository.save(user);
		
	}

}
