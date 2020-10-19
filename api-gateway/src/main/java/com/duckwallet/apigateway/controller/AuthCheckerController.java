package com.duckwallet.apigateway.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-checker")
@CrossOrigin("*")
public class AuthCheckerController {

    @GetMapping
    public String isAuthenticated() throws JSONException {
        JSONObject jsonObject = new JSONObject("{result: ok}");
        return jsonObject.toString();
    }
}
