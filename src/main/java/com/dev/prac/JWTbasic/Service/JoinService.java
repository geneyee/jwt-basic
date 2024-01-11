package com.dev.prac.JWTbasic.Service;

import com.dev.prac.JWTbasic.domain.user.Role;
import com.dev.prac.JWTbasic.domain.user.UserEntity;
import com.dev.prac.JWTbasic.domain.user.UserRepository;
import com.dev.prac.JWTbasic.dto.JoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JoinDTO join(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        Boolean exist = userRepository.existsByUsername(username);

        if(exist){
            throw new IllegalArgumentException();
        }

        if(username.equals("admin")){
            UserEntity user = UserEntity.builder()
                            .username(joinDTO.getUsername())
                            .password(passwordEncoder.encode(joinDTO.getPassword()))
                            .role("ROLE_ADMIN") // Role.ADMIN
                            .build();

            UserEntity entity = userRepository.save(user);

            return joinDTO.of(entity);

        } else {
            UserEntity user = UserEntity.builder()
                    .username(joinDTO.getUsername())
                    .password(passwordEncoder.encode(joinDTO.getPassword()))
                    .role("ROLE_USER")
                    .build();

            UserEntity entity = userRepository.save(user);

            return joinDTO.of(entity);
        }
    }

}
