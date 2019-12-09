package com.gradproject2019.users.service;

import com.gradproject2019.auth.service.AuthServiceImpl;
import com.gradproject2019.users.data.UserPatchRequestDto;
import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.exception.*;
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
    public List<UserResponseDto> searchByName(String query) {
        return userRepository.searchByName(query).stream()
                .map(user -> UserResponseDto.UserResponseDtoBuilder.anUserResponseDto().withFirstName(user.getFirstName()).withLastName(user.getLastName()).withId(user.getId()).build())
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
        checkUserRequestingIsAuthorized(userId, token);

        return new UserResponseDto().from(getUserById(userId));
    }

    @Override
    public UserResponseDto editUser(UUID token, Long userId, UserPatchRequestDto userPatchRequestDto) {
        checkValidUpdate(token, userId, userPatchRequestDto);

        userRepository.updateUser(userId, userPatchRequestDto.getFirstName(), userPatchRequestDto.getLastName(), userPatchRequestDto.getUsername(), userPatchRequestDto.getEmail(), userPatchRequestDto.getOccupation(), userPatchRequestDto.getManagerId());
        return new UserResponseDto().from(getUserById(userId));
    }

    private void checkValidSave(UserRequestDto userRequestDto) {
        checkCredentials(userRequestDto.getPassword(), PASSWORD_VALIDATION_PATTERN);
        checkCredentials(userRequestDto.getUsername(), USERNAME_VALIDATION_PATTERN);
        checkCredentials(userRequestDto.getEmail(), EMAIL_VALIDATION_PATTERN);
        checkIfUsernameExists(userRequestDto.getUsername());
        checkIfEmailExists(userRequestDto.getEmail());
        checkIfManagerExists(userRequestDto.getManagerId());
    }

    private void checkValidUpdate(UUID token, Long userId, UserPatchRequestDto userPatchRequestDto) {
        checkTokenMatchesUser(token, userId);
        checkCredentials(userPatchRequestDto.getUsername(), USERNAME_VALIDATION_PATTERN);
        checkCredentials(userPatchRequestDto.getEmail(), EMAIL_VALIDATION_PATTERN);
        checkIfUpdatedCredentialsExist(userPatchRequestDto.getUsername(), userPatchRequestDto.getEmail(), userId);
        checkIfManagerExists(userPatchRequestDto.getManagerId());
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

    private void checkIfManagerExists(Long managerId) {
        if(managerId!=null && !userRepository.findById(managerId).isPresent()) {
            throw new ManagerNotFoundException();
        }
    }

    private void checkTokenMatchesUser(UUID token, Long userId) {
        if(!authServiceImpl.getTokenById(token).getUserId().equals(userId)) {
            throw new UserForbiddenException();
        }
    }

    private void checkUserRequestingIsAuthorized(Long requestedUserId, UUID token) {
        User requestingUser = getUserById(authServiceImpl.getTokenById(token).getUserId());
        User requestedUser = getUserById(requestedUserId);
        if (!(requestedUser.getManagerId() != null && requestingUser.getId().equals(requestedUser.getManagerId()))
                && !(requestingUser.getManagerId() != null && requestingUser.getManagerId().equals(requestedUser.getId()))) {
            checkTokenMatchesUser(token, requestedUserId);
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