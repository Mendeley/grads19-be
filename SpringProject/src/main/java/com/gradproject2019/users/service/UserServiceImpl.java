package com.gradproject2019.users.service;

import com.gradproject2019.auth.exception.TokenNotFoundException;
import com.gradproject2019.auth.exception.UserUnauthorisedException;
import com.gradproject2019.auth.persistance.Token;
import com.gradproject2019.auth.repository.AuthRepository;
import com.gradproject2019.auth.service.AuthService;
import com.gradproject2019.auth.service.AuthServiceImpl;
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

import java.util.UUID;

import static com.gradproject2019.users.data.UserRequestDto.from;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthRepository authRepository;

    public static final String PASSWORD_VALIDATION_PATTERN = "((?=.*[a-z])(?=.*[0-9])(?=.*[!?\\#@^&£$*+;:~])(?=.*[A-Z]).{8,16})";
    public static final String EMAIL_VALIDATION_PATTERN = "^[a-zA-Z0-9\\.\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`]+@[a-zA-Z0-9]+\\.[\\.A-Za-z]{1,10}";
    public static final String USERNAME_VALIDATION_PATTERN = "^[a-zA-Z0-9]*$";

    public UserServiceImpl(UserRepository userRepository, AuthRepository authRepository) {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
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
        checkCredentials(userRequestDto.getUsername(), USERNAME_VALIDATION_PATTERN);    //Split in two
        checkCredentials(userRequestDto.getEmail(), EMAIL_VALIDATION_PATTERN);
    }

    private void checkCredentials(String input, String pattern) {
        if (!AuthUtils.validate(input, pattern)) {
            throw new InvalidCredentialsException();
        }
    }

    private void userExists(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {      //Split in two
            throw new UserInfoExistsException();
        }
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new UserInfoExistsException();
        }
    }

    @Override
    public UserResponseDto editUser(UUID token, Long userId, UserPatchRequestDto userPatchRequestDto) {
        Token existingToken = authRepository.findById(token).orElseThrow(TokenNotFoundException::new);
        UserResponseDto userResponseDto = getUserById(userId);
        if(!existingToken.getUserId().equals(userResponseDto.getId())) {
            throw new UserUnauthorisedException();
        }
        //Check validation fits
        //check no duplicates // one at a time and only if changed from OG

        userRepository.updateUser(userId, userPatchRequestDto.getFirstName(), userPatchRequestDto.getLastName(), userPatchRequestDto.getUsername(), userPatchRequestDto.getEmail(), userPatchRequestDto.getOccupation());
        return getUserById(userId);
    }

    public UserResponseDto getUserById(Long userId) {
        return new UserResponseDto().from(userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new));
    }
}