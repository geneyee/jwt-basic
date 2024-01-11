package com.dev.prac.JWTbasic.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public UserEntity(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
