package com.gradproject2019.users.controller;

import com.gradproject2019.users.data.UserPatchRequestDto;
import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/users/search")
    public ResponseEntity<List<UserResponseDto>> searchByName(@RequestParam String query) {
        List<UserResponseDto> users = userService.searchByName(query);
        return ResponseEntity.ok(users);
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserResponseDto> saveUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto newUser = userService.saveUser(userRequestDto);
        return ResponseEntity.ok(newUser);
    }

    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<UserResponseDto> editUser(@RequestHeader("Authorization") UUID token, @RequestBody UserPatchRequestDto userPatchRequestDto, @PathVariable("id") Long userId) {
        UserResponseDto userResponseDto = userService.editUser(token, userId, userPatchRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@RequestHeader("Authorization") UUID token, @PathVariable("id") Long userId) {
        UserResponseDto userResponseDto = userService.findUserById(userId, token);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping(path = "/users/employee/{id}") //managers user ID
    public ResponseEntity<List<UserResponseDto>> getUserByManagerId(@RequestHeader("Authorization") UUID token, @PathVariable("id") Long managerId) {
        List<UserResponseDto> userResponseDto = userService.findUserByManagerId(token, managerId);
        return ResponseEntity.ok(userResponseDto);
    }
}