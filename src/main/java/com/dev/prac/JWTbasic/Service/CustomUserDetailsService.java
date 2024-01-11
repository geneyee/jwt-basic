package com.dev.prac.JWTbasic.Service;

import com.dev.prac.JWTbasic.domain.user.UserEntity;
import com.dev.prac.JWTbasic.domain.user.UserRepository;
import com.dev.prac.JWTbasic.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity != null) {
            return new CustomUserDetails(userEntity);
        }
        return null;
    }
}
