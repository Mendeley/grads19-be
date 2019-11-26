package com.gradproject2019.users.service;

import com.gradproject2019.users.data.UserPatchRequestDto;
import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;

public interface UserService {

    UserResponseDto saveUser(UserRequestDto userRequestDto);

    UserResponseDto editUser(Long userId, UserPatchRequestDto userPatchRequestDto, UserRequestDto userRequestDto);
}