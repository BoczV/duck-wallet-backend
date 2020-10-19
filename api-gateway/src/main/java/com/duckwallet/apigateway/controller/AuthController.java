package com.duckwallet.apigateway.controller;

import com.duckwallet.apigateway.model.AppUser;
import com.duckwallet.apigateway.security.JwtTokenServices;
import com.duckwallet.apigateway.service.UserServiceCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenServices jwtTokenServices;

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    UserServiceCaller userServiceCaller;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenServices jwtTokenServices) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody Map<String, Object> data) {
        try {
            String username = data.get("username").toString();
            String password = data.get("password").toString();
            // authenticationManager.authenticate calls loadUserByUsername in CustomUserDetailsService
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenServices.createToken(username, roles);

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("roles", roles);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/registration")
    public ResponseEntity register(@RequestBody Map<String, Object> data) {
        String firstName = data.get("firstName").toString();
        String lastName = data.get("lastName").toString();
        String username = data.get("username").toString();
        String password = data.get("password").toString();
        String email = data.get("email").toString();
        try {
            AppUser newUser = AppUser.builder()
                    .firstName(firstName).lastName(lastName)
                    .nickName(username).password(passwordEncoder.encode(password))
                    .email(email).roles(Arrays.asList("ROLE_USER")).build();
            userServiceCaller.saveUser(newUser);
            String token = jwtTokenServices.createToken(username, Arrays.asList("ROLE_USER"));

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("roles", Arrays.asList("ROLE_USER"));
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            throw new BadCredentialsException("Username/email address already in use!");
        }
    }

    /*@PostMapping
    public ResponseEntity logout(@RequestBody Map<String, Object> data){

    }*/
}
