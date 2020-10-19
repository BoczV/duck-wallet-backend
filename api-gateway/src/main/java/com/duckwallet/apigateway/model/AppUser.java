package com.duckwallet.apigateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    private Long id;

    private String firstName;

    private String lastName;

    private String nickName;

    private String password;

    private String email;

    @Builder.Default
    private List<String> roles = new ArrayList<>();
}
