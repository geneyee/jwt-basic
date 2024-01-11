package com.dev.prac.JWTbasic.dto;

import com.dev.prac.JWTbasic.domain.user.Role;
import com.dev.prac.JWTbasic.domain.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinDTO {

    private String username;
    private String password;
    private String role;

    @Builder
    public JoinDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public static JoinDTO of(UserEntity user) {
        return new JoinDTO(
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
