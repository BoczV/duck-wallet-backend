package com.duckwallet.userservice.controller;

import com.duckwallet.userservice.model.AppUser;
import com.duckwallet.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String sayHello(){
        return "<h1>Hello!</h1>";
    }

    @PostMapping("/save")
    public String saveUser(@RequestBody AppUser user) {
        userRepository.save(user);
        return "Ok";
    }

    @GetMapping("/get/{username}")
    public AppUser getUser(@PathVariable String username){
        return userRepository.findByNickName(username).get();
    }
}
