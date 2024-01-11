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
    private Role role;

    @Builder
    public JoinDTO(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Role admin(Role role) {
        this.role = Role.ADMIN;
        return role;
    }

    public void user(Role role) {
        this.role = Role.USER;
    }

    public static JoinDTO of(UserEntity user) {
        return new JoinDTO(
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
