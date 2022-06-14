package com.ecommers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommers.entities.User;
import com.ecommers.exceptions.ResourceNotFoundException1;
import com.ecommers.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService  {
	
	@Autowired
    private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException1("User", "username", email));
        return user;
	}

}
