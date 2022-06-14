package com.ecommers.controller;

import com.ecommers.payloads.JwtRequest;
import com.ecommers.payloads.JwtResponse;
import com.ecommers.security.JwtHelper;

import io.swagger.annotations.Api;

//import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Api(tags = "Auth Controller", value = "AuthController", description = "Controller for authentication")
public class AuthController {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws Exception {

        this.authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());


        String token = this.jwtHelper.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setJwtToken(token);
        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);

    }


    //for authentication
    @Autowired
    private AuthenticationManager authenticationManager;

    public void authenticate(String name, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, password));
        } catch (DisabledException e) {
            throw new Exception(" User is disabled !!");
        } catch (BadCredentialsException e) {
            throw new Exception(" Invalid username and password !!");
        }
    }


}
