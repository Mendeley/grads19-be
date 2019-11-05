package com.gradproject2019.users.service;

import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.exception.InvalidPasswordException;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import static com.gradproject2019.users.data.UserRequestDto.from;
import static com.gradproject2019.users.service.Validator.validate;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = from(userRequestDto);
        checkPassword(user.getPassword());
        // user.setPassword(hashPassword(user.getPassword));
        return new UserResponseDto().from(userRepository.saveAndFlush(user));
    }
    private void checkPassword(String password) {
        if (!validate(password)) {
            throw new InvalidPasswordException();
        }
    }
}

