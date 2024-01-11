package com.dev.prac.JWTbasic.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil { // jwt 생성 및 검증

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {

        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }
    // username 확인(검증)
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey) // 우리가 생성한 (서버) secretKey가 맞는지 확인
                .build() // build로 리턴
                .parseSignedClaims(token) // token으로 claim확인
                .getPayload().get("username", String.class); // payload에서 username 가져옴
    }

    // role 확인(검증)
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    // 토큰 만료일 확인(검증) 현재시간 넣어줌
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    // 로그인 성공 시 토큰 생성 (토큰 = header, payload, signature)
    public String createJwt(String username, String role, Long exipredMs){
        return Jwts.builder()
                .claim("username", username) // payload
                .claim("role", role) // payload
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발행 시간
                .expiration(new Date(System.currentTimeMillis() + exipredMs)) // 만료시간 = 발생시간 + exipredMs
                .signWith(secretKey) // secrekey로 signature 만들어서 암호화
                .compact(); // 생성한 jwt 리턴
    }


}
