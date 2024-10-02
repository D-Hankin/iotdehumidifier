package com.iotdehumidifier.iotdehumidifier.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.iotdehumidifier.iotdehumidifier.components.JwtComponent;
import com.iotdehumidifier.iotdehumidifier.models.LoginDto;
import com.iotdehumidifier.iotdehumidifier.models.LoginResponse;
import com.iotdehumidifier.iotdehumidifier.models.User;
import com.iotdehumidifier.iotdehumidifier.repositories.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@CrossOrigin("http://localhost:5173")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtComponent jwtCreationComponent;

    @Autowired
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public UserController(PasswordEncoder passwordEncoder) {
        this.authenticationManager = null;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("/create-account")
    public User getMethodName(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.insert(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginDto user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    user.getUsername(), 
                    user.getPassword())
            );
            String token = jwtCreationComponent.createToken(authentication);
            return new LoginResponse(token);
        } catch (AuthenticationException e) {
            return new LoginResponse(e.getMessage());
        }
    }
    
    
}
