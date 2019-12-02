package com.gradproject2019.users.service;

import com.gradproject2019.auth.exception.UserUnauthorisedException;
import com.gradproject2019.auth.service.AuthServiceImpl;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.users.data.UserPatchRequestDto;
import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.exception.InvalidCredentialsException;
import com.gradproject2019.users.exception.UserInfoExistsException;
import com.gradproject2019.users.exception.UserNotFoundException;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.users.repository.UserRepository;
import com.gradproject2019.utils.AuthUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gradproject2019.users.data.UserRequestDto.from;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthServiceImpl authServiceImpl;

    public static final String PASSWORD_VALIDATION_PATTERN = "((?=.*[a-z])(?=.*[0-9])(?=.*[!?\\#@^&£$*+;:~])(?=.*[A-Z]).{8,16})";
    public static final String EMAIL_VALIDATION_PATTERN = "^[a-zA-Z0-9\\.\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`]+@[a-zA-Z0-9]+\\.[\\.A-Za-z]{1,10}";
    public static final String USERNAME_VALIDATION_PATTERN = "^[a-zA-Z0-9]*$";

    public UserServiceImpl(UserRepository userRepository, AuthServiceImpl authServiceImpl) {
        this.userRepository = userRepository;
        this.authServiceImpl = authServiceImpl;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto().from(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        checkValidSave(userRequestDto);

        User user = from(userRequestDto);
        user.setPassword(AuthUtils.hash(userRequestDto.getPassword()));

        return new UserResponseDto().from(userRepository.saveAndFlush(user));
    }

    @Override
    public UserResponseDto findUserById(Long userId, UUID token) {
        authServiceImpl.getTokenById(token);
        return new UserResponseDto().from(getUserById(userId));
    }

    @Override
    public UserResponseDto editUser(UUID token, Long userId, UserPatchRequestDto userPatchRequestDto) {
        checkValidUpdate(token, userId, userPatchRequestDto);

        userRepository.updateUser(userId, userPatchRequestDto.getFirstName(), userPatchRequestDto.getLastName(), userPatchRequestDto.getUsername(), userPatchRequestDto.getEmail(), userPatchRequestDto.getOccupation());
        return new UserResponseDto().from(getUserById(userId));
    }

    private void checkValidSave(UserRequestDto userRequestDto) {
        checkCredentials(userRequestDto.getPassword(), PASSWORD_VALIDATION_PATTERN);
        checkCredentials(userRequestDto.getUsername(), USERNAME_VALIDATION_PATTERN);
        checkCredentials(userRequestDto.getEmail(), EMAIL_VALIDATION_PATTERN);
        checkIfUsernameExists(userRequestDto.getUsername());
        checkIfEmailExists(userRequestDto.getEmail());
    }

    private void checkValidUpdate(UUID token, Long userId, UserPatchRequestDto userPatchRequestDto) {
        checkTokenMatchesUser(token, userId);
        checkCredentials(userPatchRequestDto.getUsername(), USERNAME_VALIDATION_PATTERN);
        checkCredentials(userPatchRequestDto.getEmail(), EMAIL_VALIDATION_PATTERN);
        checkIfUpdatedCredentialsExist(userPatchRequestDto.getUsername(), userPatchRequestDto.getEmail(), userId);
    }

    private void checkCredentials(String input, String pattern) {
        if(input != null) {
            if (!AuthUtils.validate(input, pattern)) {
                throw new InvalidCredentialsException();
            }
        }
    }

    private void checkIfUsernameExists(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserInfoExistsException();
        }
    }

    private void checkIfEmailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserInfoExistsException();
        }
    }

    private User getUserById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private void checkTokenMatchesUser(UUID token, Long userId) {
        if(!authServiceImpl.getTokenById(token).getUserId().equals(userId)) {
            throw new UserUnauthorisedException();
        }
    }

    private void checkIfUpdatedCredentialsExist(String patchUsername, String patchEmail, Long userId) {
        User user = getUserById(userId);
        if(patchUsername != null && !patchUsername.equals(user.getUsername())) {
            checkIfUsernameExists(patchUsername);
        }
        if(patchEmail != null && !patchEmail.equals(user.getEmail())) {
            checkIfEmailExists(patchEmail);
        }
    }
}