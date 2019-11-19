package com.gradproject2019.users.service;

import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.exception.CredentialsExistException;
import com.gradproject2019.users.exception.InvalidEmailFormatException;
import com.gradproject2019.users.exception.InvalidPasswordFormatException;
import com.gradproject2019.users.exception.InvalidUsernameFormatException;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.users.repository.UserRepository;
import com.gradproject2019.utils.EmailUtils;
import com.gradproject2019.utils.PasswordUtils;
import com.gradproject2019.utils.UsernameUtils;
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
        String password = userRequestDto.getPassword();
        String username = userRequestDto.getUsername();
        String email = userRequestDto.getEmail();

        usernameExists(username);
        emailExists(email);

        checkPassword(password);
        checkUsername(username);
        checkEmail(email);

        User user = from(userRequestDto);
        user.setPassword(PasswordUtils.hash(password));

        return new UserResponseDto().from(userRepository.saveAndFlush(user));
    }

    private void checkPassword(String password) {
        if (!PasswordUtils.validate(password)) {
            throw new InvalidPasswordFormatException();
        }
    }

    private void checkUsername(String username) {
        if (!UsernameUtils.validate(username)) {
            throw new InvalidUsernameFormatException();
        }
    }

    private void checkEmail(String email) {
        if (!EmailUtils.validate(email)) {
            throw new InvalidEmailFormatException();
        }
    }

    private void usernameExists(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new CredentialsExistException();
        }
    }

    private void emailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new CredentialsExistException();
        }
    }
}

