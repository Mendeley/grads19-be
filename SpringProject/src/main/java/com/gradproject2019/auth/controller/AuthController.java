package com.gradproject2019.auth.controller;

import com.gradproject2019.auth.data.LoginDto;
import com.gradproject2019.auth.persistance.Token;
import com.gradproject2019.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/auth/login")
    public ResponseEntity<Token> login(@Valid @RequestBody LoginDto loginDto) {
        Token token = authService.login(loginDto);
        return ResponseEntity.ok(token);
    }

}
