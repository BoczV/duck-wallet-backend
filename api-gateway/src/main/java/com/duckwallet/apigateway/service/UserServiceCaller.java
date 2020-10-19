package com.duckwallet.apigateway.service;

import com.duckwallet.apigateway.model.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@Slf4j
public class UserServiceCaller {

    @Autowired
    private RestTemplate template;

    private final String basicUrl = "http://userservice/";

    public String getHello(){
        return template.getForEntity(basicUrl + "/user", String.class).getBody();
    }

    public String saveUser(AppUser user) {
        return template.postForEntity(basicUrl + "/user/save", user, String.class).getBody();
    }

    public AppUser getUser(String username){
        return template.getForEntity(basicUrl + "/user/get/" + username, AppUser.class).getBody();
    }


}
