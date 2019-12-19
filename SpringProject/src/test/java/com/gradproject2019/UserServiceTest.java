package com.gradproject2019;

import com.gradproject2019.auth.persistence.Token;
import com.gradproject2019.auth.service.AuthServiceImpl;
import com.gradproject2019.users.data.UserPatchRequestDto;
import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.exception.InvalidCredentialsException;
import com.gradproject2019.users.exception.UserForbiddenException;
import com.gradproject2019.users.exception.UserInfoExistsException;
import com.gradproject2019.users.exception.UserNotFoundException;
import com.gradproject2019.users.persistence.User;
import com.gradproject2019.users.repository.UserRepository;
import com.gradproject2019.users.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthServiceImpl authServiceImpl;

    private final User qwerty = new User(1L, "qwerty", "qwerty", "qwerty", "qwerty@qwerty.com", "Qwerty!1", "qwerty", null);
    private final User qwerty2 = new User(2L, "qwerty2", "qwerty2", "qwerty2", "qwerty2@qwerty.com", "Qwerty!1", "qwerty2", 3L);
    private final Token token = new Token(1L, UUID.randomUUID());
    private final Long userId = 1L;
    private final Long userId2 = 2L;


    private void setUpUserAndToken(User user, Token token) {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
    }

    @Test(expected = UserInfoExistsException.class)
    public void shouldThrowErrorWhenRegistrationUsernameExists() {
        given(userRepository.findByUsername("qwerty")).willReturn(Optional.of(qwerty));
        UserRequestDto copycat = createUserRequestDto("notqwerty@qwerty.com", "qwerty");

        userService.saveUser(copycat);
    }

    @Test(expected = UserInfoExistsException.class)
    public void shouldThrowErrorWhenRegistrationEmailExists() {
        given(userRepository.findByEmail("qwerty@qwerty.com")).willReturn(Optional.of(qwerty));
        UserRequestDto copycat = createUserRequestDto("qwerty@qwerty.com", "notqwerty");

        userService.saveUser(copycat);
    }

    @Test(expected = UserForbiddenException.class)
    public void shouldThrowErrorWhenTokenDoesNotMatchUserId() {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        UserPatchRequestDto update = createUserPatchRequestDto("newqwerty@newqwerty.com", "newqwerty");

        userService.editUser(token.getToken(), 2L, update);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void shouldThrowErrorWhenInvalidUsernameFormat() {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        UserPatchRequestDto update = createUserPatchRequestDto("newqwerty@newqwerty.com", "wrong format");

        userService.editUser(token.getToken(), userId, update);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void shouldThrowErrorWhenInvalidEmailFormat() {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        UserPatchRequestDto update = createUserPatchRequestDto("not an email", "newusername");

        userService.editUser(token.getToken(), userId, update);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowErrorWhenUserToBeEditedDoesNotExist() {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        given(userRepository.findById(userId)).willReturn(Optional.empty());
        UserPatchRequestDto update = createUserPatchRequestDto("newqwerty@newqwerty.com", "newqwerty");

        userService.editUser(token.getToken(), userId, update);
    }

    @Test(expected = UserInfoExistsException.class)
    public void shouldThrowErrorWhenNewUsernameExist() {
        setUpUserAndToken(qwerty, token);
        UserPatchRequestDto update = createUserPatchRequestDto("newqwerty@newqwerty.com", "newqwerty");
        given(userRepository.findByUsername(update.getUsername())).willReturn(Optional.of(createUser("notqwerty@qwerty.com", "newqwerty")));

        userService.editUser(token.getToken(), userId, update);
    }

    @Test(expected = UserInfoExistsException.class)
    public void shouldThrowErrorWhenNewEmailExists() {
        setUpUserAndToken(qwerty, token);
        UserPatchRequestDto update = createUserPatchRequestDto("newqwerty@newqwerty.com", "newqwerty");
        given(userRepository.findByUsername(update.getUsername())).willReturn(Optional.empty());
        given(userRepository.findByEmail(update.getEmail())).willReturn(Optional.of(createUser("newqwerty@newqwerty.com", "notqwerty")));

        userService.editUser(token.getToken(), userId, update);
    }

    @Test(expected = UserForbiddenException.class)
    public void shouldThrowErrorWhenRequestingManagerDoesNotMatchRequestedUser() {
        setUpUserAndToken(qwerty, token);
        given(userRepository.findById(userId2)).willReturn(Optional.of(qwerty2));
        given(userRepository.hasManagerEmployeeRelationship(userId2, userId, qwerty.getManagerId())).willReturn(0);

        userService.findUserById(userId2, token.getToken());
    }

    @Test
    public void shouldPassWhenRequestingUserDoesMatchRequestedUser() {
        setUpUserAndToken(qwerty, token);
        given(userRepository.hasManagerEmployeeRelationship(userId, userId, qwerty.getManagerId())).willReturn(0);

        UserResponseDto response = userService.findUserById(userId, token.getToken());

        Assert.assertEquals(qwerty.getId(), response.getId());
        Assert.assertEquals(qwerty.getFirstName(), response.getFirstName());
    }

    private UserRequestDto createUserRequestDto(String email, String username) {
        return UserRequestDto
                .UserRequestDtoBuilder
                .anUserRequestDto()
                .withFirstName("notqwerty")
                .withLastName("notqwerty")
                .withEmail(email)
                .withOccupation("notqwerty")
                .withPassword("NotQwerty!1")
                .withUsername(username)
                .build();
    }

    private UserPatchRequestDto createUserPatchRequestDto(String email, String username) {
        return UserPatchRequestDto
                .UserPatchRequestDtoBuilder
                .anUserPatchRequestDto()
                .withFirstName("notqwerty")
                .withLastName("notqwerty")
                .withEmail(email)
                .withOccupation("notqwerty")
                .withUsername(username)
                .build();
    }

    private User createUser(String email, String username) {
        return User
                .UserBuilder
                .anUser()
                .withId(2L)
                .withFirstName("notqwerty")
                .withLastName("notqwerty")
                .withEmail(email)
                .withOccupation("notqwerty")
                .withPassword("password")
                .withUsername(username)
                .build();
    }
}