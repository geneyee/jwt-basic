package com.dev.prac.JWTbasic.dto;

import lombok.Getter;

@Getter
public class LoginDTO {

    private String username;
    private String role;

    public LoginDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }
}
