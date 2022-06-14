package com.ecommers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommers.entities.Role;
import com.ecommers.entities.User;
import com.ecommers.repository.RoleRepo;
import com.ecommers.repository.UserRepository;

@SpringBootApplication
public class ECommersShopNowApplication implements CommandLineRunner  {
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(ECommersShopNowApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	
	  public void run(String... args) throws Exception {
		  
		 /*  try {
	            Role role = new Role();
	            role.setId(101);
	            role.setName("ROLE_NORMAL");

	            Role role1 = new Role();
	            role1.setId(102);
	            role1.setName("ROLE_ADMIN");
	            List<Role> roles = List.of(role, role1);

	            List<Role> roles1 = this.roleRepo.saveAll(roles);
	            roles1.forEach(e -> {
	                System.out.println(e.getName());
	            });


	        } catch (Exception e) {
	            e.printStackTrace();
	        }*/

	
//	        Role role1 = new Role();
//	        role1.setId(4);
//	        role1.setName("ROLE_ADMIN");
//	
//	        Role role2 = new Role();
//	        role2.setId(5);
//	        role2.setName("ROLE_NORMAL");
//	
//	        Set<Role> roles = new HashSet<>();
//	
//	        roles.add(role1);
//	        roles.add(role2);
//	
//	        User user = new User();
//	        user.setName("sumit");
//	        user.setEmail("sumit@gmail.com");
//	        user.setPassword(this.passwordEncoder.encode("sumit"));
//	        user.setAddress("Moradabad");
//	        user.setImageName("default.png");
//	        user.setRoles(roles);
//	        user.setAbout("I am very good programmer.");
//	        User save = this.userRepository.save(user);
//	        System.out.println(save);
	    }

}
