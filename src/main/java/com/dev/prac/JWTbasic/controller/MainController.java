package com.dev.prac.JWTbasic.controller;

import com.dev.prac.JWTbasic.Service.MainService;
import com.dev.prac.JWTbasic.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
@RestController
public class MainController {

    private final MainService mainService;

    @GetMapping("/main")
    public ResponseEntity<LoginDTO> main() {

        LoginDTO dto =  mainService.getInfo();

        return ResponseEntity.ok().body(dto);
    }
}
