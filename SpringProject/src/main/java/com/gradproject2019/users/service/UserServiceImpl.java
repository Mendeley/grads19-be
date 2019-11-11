package com.gradproject2019.users.service;

import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.exception.InvalidPasswordException;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.users.repository.UserRepository;
import com.gradproject2019.users.service.PasswordService;
import org.springframework.stereotype.Service;

import static com.gradproject2019.users.data.UserRequestDto.from;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordService passwordService;

    public UserServiceImpl(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = from(userRequestDto);
        checkPassword(user.getPassword());
       passwordService.hash(userRequestDto.getPassword());
        return new UserResponseDto().from(userRepository.saveAndFlush(user));
    }
    private void checkPassword(String password) {
        if (!passwordService.validate(password)) {
            throw new InvalidPasswordException();
        }
    }

}

