package com.dev.prac.JWTbasic.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 클라이언트 요청에서 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        log.info("username => {}", username);

        // 검증을 위해 Authentication Manager로 보낼 때 token 타입이어야 함
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(token);
    }

    // 검증 후 로그인 성공시 실행하는 메서드(여기서 jwt 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        log.info("===================로그인 성공=========================");
    }

    // 검증 후 로그인 실패시 실행하는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("===================로그인 실패=========================");

    }
}
