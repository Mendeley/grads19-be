package com.gradproject2019.users.service;

import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.exception.InvalidPasswordFormatException;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.users.repository.UserRepository;
import com.gradproject2019.utils.PasswordUtils;
import org.springframework.stereotype.Service;

import static com.gradproject2019.users.data.UserRequestDto.from;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private final PasswordUtils passwordUtils;

    public UserServiceImpl(UserRepository userRepository, PasswordUtils passwordUtils) {
        this.userRepository = userRepository;
        this.passwordUtils = passwordUtils;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = from(userRequestDto);
        String password = user.getPassword();
        checkPassword(password);
        user.setPassword(passwordUtils.hash(password));
        return new UserResponseDto().from(userRepository.saveAndFlush(user));
    }
    private void checkPassword(String password) {
        if (!passwordUtils.validate(password)) {
            throw new InvalidPasswordFormatException();
        }
    }

}

