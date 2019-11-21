package com.gradproject2019.users.service;

import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.exception.CredentialsExistException;
import com.gradproject2019.users.exception.InvalidEmailFormatException;
import com.gradproject2019.users.exception.InvalidPasswordFormatException;
import com.gradproject2019.users.exception.InvalidUsernameFormatException;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.users.repository.UserRepository;
import com.gradproject2019.utils.AuthUtils;
import org.springframework.stereotype.Service;

import static com.gradproject2019.users.data.UserRequestDto.from;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public static final String PASSWORD_VALIDATION_PATTERN = "((?=.*[a-z])(?=.*[0-9])(?=.*[!?\\#@^&£$*+;:~])(?=.*[A-Z]).{8,16})";
    public static final String EMAIL_VALIDATION_PATTERN = "^[a-zA-Z0-9\\.\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`]+@[a-zA-Z0-9]+\\.[\\.A-Za-z]{1,10}";
    public static final String USERNAME_VALIDATION_PATTERN = "^[a-zA-Z0-9]*$";

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
        user.setPassword(AuthUtils.hash(password));

        return new UserResponseDto().from(userRepository.saveAndFlush(user));
    }

    private void checkPassword(String password) {
        if (!AuthUtils.validate(password, PASSWORD_VALIDATION_PATTERN)) {

            throw new InvalidPasswordFormatException();
        }
    }

    private void checkUsername(String username) {
        if (!AuthUtils.validate(username, USERNAME_VALIDATION_PATTERN)) {
            throw new InvalidUsernameFormatException();
        }
    }

    private void checkEmail(String email) {
        if (!AuthUtils.validate(email, EMAIL_VALIDATION_PATTERN)) {
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

