package com.gradproject2019.users.controller;

import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/users")
    public ResponseEntity<UserResponseDto> saveUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto newUser = userService.saveUser(userRequestDto);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@RequestHeader("Authorization") UUID token, @PathVariable("id") Long userId) {
        UserResponseDto userResponseDto = userService.getUserById(userId, token);
        return ResponseEntity.ok(userResponseDto);
    }
}
