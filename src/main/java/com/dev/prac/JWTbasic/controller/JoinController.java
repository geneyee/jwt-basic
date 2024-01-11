package com.dev.prac.JWTbasic.controller;

import com.dev.prac.JWTbasic.Service.JoinService;
import com.dev.prac.JWTbasic.dto.JoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<JoinDTO> join(@RequestBody JoinDTO joinDTO) {

        JoinDTO dto = joinService.join(joinDTO);

        return ResponseEntity.ok().body(dto);
    }
}
