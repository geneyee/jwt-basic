package com.dev.prac.JWTbasic.config;

import com.dev.prac.JWTbasic.domain.user.Role;
import com.dev.prac.JWTbasic.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


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
        // 경로별 인가 작업
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/image/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/", "/main","/login", "/join").permitAll()
                        .requestMatchers("/admin").hasRole(Role.ADMIN.name()) // "ADMIN"
                        .anyRequest().authenticated()
                );

        // formLogin disable 했으므로 그에 해당하는 LoginFilter()를 추가한다.(AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함)
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);
        // 세션 설정 - jwt 인증/인가에서는 session stateless 상태
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
