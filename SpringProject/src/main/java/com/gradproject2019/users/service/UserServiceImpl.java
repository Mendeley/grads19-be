package com.gradproject2019.users.service;

import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.exception.InvalidEmailFormatException;
import com.gradproject2019.users.exception.InvalidPasswordFormatException;
import com.gradproject2019.users.exception.InvalidUsernameFormatException;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.users.repository.UserRepository;
import com.gradproject2019.utils.PasswordUtils;
import org.springframework.stereotype.Service;

import static com.gradproject2019.users.data.UserRequestDto.from;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = from(userRequestDto);
        String password = user.getPassword();
        String username = user.getUsername();
        String email = user.getEmail();
        checkPassword(password);
        checkUsername(username);
        checkEmail(email);
        user.setPassword(PasswordUtils.hash(password));
        return new UserResponseDto().from(userRepository.saveAndFlush(user));
    }
    private void checkPassword(String password) {
        if (!PasswordUtils.validate(password)) {
            throw new InvalidPasswordFormatException();
        }
    }
    private void checkUsername(String username) {
        if (!PasswordUtils.validate(username)) {
            throw new InvalidUsernameFormatException();
        }
    }
    private void checkEmail(String email) {
        if (!PasswordUtils.validate(email)) {
            throw new InvalidEmailFormatException();
        }
    }

    //check if username has already been used
}

