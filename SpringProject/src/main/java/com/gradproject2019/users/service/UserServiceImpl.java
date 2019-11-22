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
    //Password must contain one capital letter, one lowercase letter, one special character and one number
    //Password must also be between 8 and 16 characters in length
    public static final String EMAIL_VALIDATION_PATTERN = "^[a-zA-Z0-9\\.\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`]+@[a-zA-Z0-9]+\\.[\\.A-Za-z]{1,10}";
    //Email must contain an '@' and a '.'
    //Email must not contain spaces
    //Email can only contain lowercase, uppercase, numbers and the following special characters ".!#$%&'*+-/=?^_`"
    public static final String USERNAME_VALIDATION_PATTERN = "^[a-zA-Z0-9]*$";
    //Username must not contain spaces
    //Username can only contain lowercase, uppercase and numbers

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        checkRegexValidity(userRequestDto);
        userExists(userRequestDto);

        User user = from(userRequestDto);
        user.setPassword(AuthUtils.hash(userRequestDto.getPassword()));

        return new UserResponseDto().from(userRepository.saveAndFlush(user));
    }

    private void checkRegexValidity(UserRequestDto userRequestDto) {
        checkRegex(userRequestDto.getPassword(), PASSWORD_VALIDATION_PATTERN);
        checkRegex(userRequestDto.getUsername(), USERNAME_VALIDATION_PATTERN);
        checkRegex(userRequestDto.getEmail(), EMAIL_VALIDATION_PATTERN);
    }

    private void checkRegex(String input, String pattern) {
        if (!AuthUtils.validate(input, pattern)) {
            throw new InvalidRegexFormatException();
        }
    }

    private void userExists(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {
            throw new UserInfoExistsException();
        }
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new UserInfoExistsException();
        }
    }
}