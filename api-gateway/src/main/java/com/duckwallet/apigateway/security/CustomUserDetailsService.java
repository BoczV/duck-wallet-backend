package com.duckwallet.apigateway.security;


import com.duckwallet.apigateway.model.AppUser;
import com.duckwallet.apigateway.service.UserServiceCaller;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {


    private UserServiceCaller userServiceCaller;

    public CustomUserDetailsService(UserServiceCaller userServiceCaller) {
        this.userServiceCaller = userServiceCaller;
    }

    /**
     * Loads the user from the DB and converts it to Spring Security's internal User object.
     * Spring will call this code to retrieve a user upon login from the DB.
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            AppUser user = userServiceCaller.getUser(username);
            return new org.springframework.security.core.userdetails.User(user.getNickName(), user.getPassword(),
                    user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        } catch (NullPointerException exception) {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }
    }
}
