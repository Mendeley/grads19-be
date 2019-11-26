package com.gradproject2019.users.service;

import com.gradproject2019.users.data.UserPatchRequestDto;
import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.exception.UserInfoExistsException;
import com.gradproject2019.users.exception.InvalidCredentialsException;
import com.gradproject2019.users.exception.UserNotFoundException;
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
        checkCredentialsValidity(userRequestDto);
        userExists(userRequestDto);

        User user = from(userRequestDto);
        user.setPassword(AuthUtils.hash(userRequestDto.getPassword()));

        return new UserResponseDto().from(userRepository.saveAndFlush(user));
    }

    private void checkCredentialsValidity(UserRequestDto userRequestDto) {
        checkCredentials(userRequestDto.getPassword(), PASSWORD_VALIDATION_PATTERN);
        checkCredentials(userRequestDto.getUsername(), USERNAME_VALIDATION_PATTERN);
        checkCredentials(userRequestDto.getEmail(), EMAIL_VALIDATION_PATTERN);
    }

    private void checkCredentials(String input, String pattern) {
        if (!AuthUtils.validate(input, pattern)) {
            throw new InvalidCredentialsException();
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

    public UserResponseDto editUser(Long userId, UserPatchRequestDto userPatch, UserRequestDto userRequestDto){
        userExists(userRequestDto);

        userRepository.updateUser(userId, userPatch.getFirstName(), userPatch.getLastName(), userPatch.getUsername(), userPatch.getEmail(), userPatch.getOccupation());

        return getUserById(userId);
    }

    public UserResponseDto getUserById(Long userId) {
        return new UserResponseDto().from(userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new));
    }
}