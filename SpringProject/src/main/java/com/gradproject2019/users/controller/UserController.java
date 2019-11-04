package com.gradproject2019.users.controller;

import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

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
}
