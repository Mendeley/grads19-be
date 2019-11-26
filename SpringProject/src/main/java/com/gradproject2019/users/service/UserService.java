package com.gradproject2019.users.service;

import com.gradproject2019.users.data.UserPatchRequestDto;
import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;

import java.util.UUID;

public interface UserService {

    UserResponseDto saveUser(UserRequestDto userRequestDto);
    UserResponseDto findUserById(Long userId, UUID token);
    UserResponseDto editUser(Long userId, UserPatchRequestDto userPatchRequestDto, UserRequestDto userRequestDto);
}
