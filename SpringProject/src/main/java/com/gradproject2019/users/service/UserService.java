package com.gradproject2019.users.service;

import com.gradproject2019.users.data.UserPatchRequestDto;
import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;

import java.util.UUID;

public interface UserService {

    UserResponseDto saveUser(UserRequestDto userRequestDto);

    UserResponseDto editUser(UUID token, Long userId, UserPatchRequestDto userPatchRequestDto);
}