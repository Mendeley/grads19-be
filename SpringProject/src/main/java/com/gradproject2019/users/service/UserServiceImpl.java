package com.gradproject2019.users.service;

import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.exception.UserInfoExistsException;
import com.gradproject2019.users.exception.InvalidRegexFormatException;
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

        checkRegex(password, PASSWORD_VALIDATION_PATTERN);
        checkRegex(username, USERNAME_VALIDATION_PATTERN);
        checkRegex(email, EMAIL_VALIDATION_PATTERN);

        User user = from(userRequestDto);
        user.setPassword(AuthUtils.hash(password));

        return new UserResponseDto().from(userRepository.saveAndFlush(user));
    }

    private void checkRegex(String input, String pattern) {
        if (!AuthUtils.validate(input, pattern)) {
            throw new InvalidRegexFormatException();
        }
    }

    private void usernameExists(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserInfoExistsException();
        }
    }

    private void emailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserInfoExistsException();
        }
    }
}

