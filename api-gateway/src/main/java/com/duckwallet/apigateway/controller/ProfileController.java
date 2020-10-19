package com.duckwallet.apigateway.controller;

import com.duckwallet.apigateway.security.JwtTokenServices;
import com.duckwallet.apigateway.service.UserServiceCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@CrossOrigin("*")
public class ProfileController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenServices jwtTokenServices;

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    UserServiceCaller userServiceCaller;

    public ProfileController(AuthenticationManager authenticationManager, JwtTokenServices jwtTokenServices) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
    }

    @GetMapping("/hello")
    public String getHello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token_username = (String) authentication.getPrincipal();
        System.out.println("tokenusername:"+token_username);

        return userServiceCaller.getHello();
    }
}
