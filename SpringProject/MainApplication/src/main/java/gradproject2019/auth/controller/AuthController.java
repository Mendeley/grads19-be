package gradproject2019.auth.controller;


import gradproject2019.auth.data.LoginDto;
import gradproject2019.auth.persistence.Token;
import gradproject2019.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

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

    @DeleteMapping(path = "/auth/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") UUID token) {
        authService.logout(token);
        return ResponseEntity.noContent().build();
    }
}