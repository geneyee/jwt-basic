package com.dev.prac.JWTbasic.config;

import com.dev.prac.JWTbasic.domain.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // csrf disable
        http
                .csrf(csrf -> csrf.disable());
        // formLogin 방식 disable
        http
                .formLogin(formLogin -> formLogin.disable());
        // http basic 인증 방식 disable
        http
                .httpBasic(basic -> basic.disable());
        // 경로 별 인가 작업
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/image/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/", "/main","/login", "/join").permitAll()
                        .requestMatchers("/admin").hasRole(Role.ADMIN.name()) // "ADMIN"
                        .anyRequest().authenticated()
                );
        // 세션 설정 - jwt 인증/인가에서는 session stateless 상태
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
