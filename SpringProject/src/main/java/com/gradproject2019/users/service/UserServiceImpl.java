package com.gradproject2019.users.service;

import com.gradproject2019.auth.exception.UserUnauthorisedException;
import com.gradproject2019.auth.persistance.Token;
import com.gradproject2019.auth.repository.AuthRepository;
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
        String password = userRequestDto.getPassword();
        String username = userRequestDto.getUsername();
        String email = userRequestDto.getEmail();

        checkPasswordValidity(password);
        checkUsernameAndEmailValidity(username, email);
        checkIfUsernameExists(username);
        checkIfEmailExists(email);

        User user = from(userRequestDto);
        user.setPassword(AuthUtils.hash(password));

        return new UserResponseDto().from(userRepository.saveAndFlush(user));
    }

    @Override
    public UserResponseDto editUser(UUID token, Long userId, UserPatchRequestDto userPatch) {
        String patchUsername = userPatch.getUsername();
        String patchEmail = userPatch.getEmail();

        Token existingToken = getTokenByUUID(token);
        User user = getUserById(userId);
        checkTokenMatchesUser(existingToken.getUserId(), userId);
        checkUsernameAndEmailValidity(patchUsername, patchEmail);
        checkIfUpdatedCredentialsExist(patchUsername, patchEmail, user);
        //fix tests
        userRepository.updateUser(userId, userPatch.getFirstName(), userPatch.getLastName(), patchUsername, patchEmail, userPatch.getOccupation());
        return new UserResponseDto().from(getUserById(userId));
    }

    private void checkPasswordValidity(String password) {
        checkCredentials(password, PASSWORD_VALIDATION_PATTERN);
    }

    private void checkUsernameAndEmailValidity(String username, String email) {
        if(username != null) {
            checkCredentials(username, USERNAME_VALIDATION_PATTERN);
        }
        if(email != null) {
            checkCredentials(email, EMAIL_VALIDATION_PATTERN);
        }
    }

    private void checkCredentials(String input, String pattern) {
        if (!AuthUtils.validate(input, pattern)) {
            throw new InvalidCredentialsException();
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

    private Token getTokenByUUID(UUID token) {
        return authRepository
                .findById(token)
                .orElseThrow(UserUnauthorisedException::new);
    }

    private void checkTokenMatchesUser(Long tokenUserId, Long userId) {
        if(!tokenUserId.equals(userId)) {
            throw new UserUnauthorisedException();
        }
    }

    private void checkIfUpdatedCredentialsExist(String patchUsername, String patchEmail, User user) {
        if(patchUsername != null && !patchUsername.equals(user.getUsername())) {
            checkIfUsernameExists(patchUsername);
        }
        if(patchEmail != null && !patchEmail.equals(user.getEmail())) {
            checkIfUsernameExists(patchEmail);
        }
    }
}