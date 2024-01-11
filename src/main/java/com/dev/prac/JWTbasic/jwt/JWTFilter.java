package com.dev.prac.JWTbasic.jwt;

import com.dev.prac.JWTbasic.domain.user.Role;
import com.dev.prac.JWTbasic.domain.user.UserEntity;
import com.dev.prac.JWTbasic.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JWT 검증 필터
@Log4j2
public class JWTFilter extends OncePerRequestFilter {
    // 로그인을 하면 jwt가 발급되고 발급된 jwt는 header에 response된다. (이때, key가 Authorization)
    // .permitAll() 설정을 하지 않은 나머지 요청(시큐리티 filter chain)에 대해서는 jwt의 검증이 필수이다.
    // jwt 검증을 위한 커스텀 필터를 등록하고,
    // 이 필터를 통해 요청 헤더 Authorization 키에 jwt가 존재하는 경우 jwt를 검증하고
    // 강제로 SecurityContextHolder에 세션을 생성한다.(이 세션은 stateless이므로 해당 요청이 끝나면 바로 소멸)

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증 - Bearer 로 시작하는지 확인
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("================ token null ==============");
            filterChain.doFilter(request, response);

            // 조건에 해당되면 메서드 종료(중요)
            return;
        }

        log.info("============ authorization now ============");

        // Bearer 제거 후 토큰만 획득([0] = "Bearer"
        String token = authorization.split(" ")[1];

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) { // 토큰 소멸되었다면 true
            log.info("============ token expired =============");
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization 헤더 o, 토큰 소멸 시간 남음
        // 토큰에서 username, role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // 세션에 일시적으로 user정보 저장하기 위해 UserEntity 초기화
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password("temppassword")
                .role(role)
                .build();

        log.info("role => {}", userEntity.getRole());

        // UserDetails에 userEntity 저장
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 세션에 사용자 정보 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
