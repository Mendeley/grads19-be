package gradproject2019.users.service;

import gradproject2019.users.data.UserPatchRequestDto;
import gradproject2019.users.data.UserRequestDto;
import gradproject2019.users.data.UserResponseDto;
import gradproject2019.users.persistence.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserResponseDto> searchByName(String query);

    UserResponseDto saveUser(UserRequestDto userRequestDto);

    UserResponseDto editUser(UUID token, Long userId, UserPatchRequestDto userPatchRequestDto);

    UserResponseDto findUserById(Long userId, UUID token);

    List<UserResponseDto> getUsers(UUID token, Long managerId);

    User findByUsername(String username);
}