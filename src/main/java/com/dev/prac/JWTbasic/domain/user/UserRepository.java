package com.dev.prac.JWTbasic.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    // username 있는지 확인 있으면 true, 없으면 false
    Boolean existsByUsername(String username);
}
