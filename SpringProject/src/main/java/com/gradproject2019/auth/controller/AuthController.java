package com.gradproject2019.auth.controller;

import com.gradproject2019.auth.data.LoginDto;
import com.gradproject2019.auth.persistence.Token;
import com.gradproject2019.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<Token> login(@Valid @RequestBody LoginDto loginDto) {
        Token token = authService.login(loginDto);
        return ResponseEntity.ok(token);
    }

    @DeleteMapping(path = "/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") UUID token) {
        authService.logout(token);
        return ResponseEntity.noContent().build();
    }
}