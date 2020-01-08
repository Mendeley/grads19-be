package java.com.gradproject2019;

import gradproject2019.auth.persistence.Token;
import gradproject2019.auth.service.AuthServiceImpl;
import gradproject2019.users.data.UserPatchRequestDto;
import gradproject2019.users.data.UserRequestDto;
import gradproject2019.users.data.UserResponseDto;
import gradproject2019.users.exception.InvalidCredentialsException;
import gradproject2019.users.exception.UserForbiddenException;
import gradproject2019.users.exception.UserInfoExistsException;
import gradproject2019.users.exception.UserNotFoundException;
import gradproject2019.users.persistence.User;
import gradproject2019.users.repository.UserRepository;
import gradproject2019.users.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthServiceImpl authServiceImpl;


    private final User user = new User(1L, "qwerty", "qwerty", "qwerty", "qwerty@qwerty.com", "Qwerty!1", "qwerty", null);
    private final User user2 = new User(2L, "qwerty2", "qwerty2", "qwerty2", "qwerty2@qwerty.com", "Qwerty!1", "qwerty2", 3L);

    private final Token token = new Token(1L, UUID.randomUUID());
    private final Token managerToken = new Token(3L, UUID.randomUUID());

    private final Long userId = 1L;
    private final Long userId2 = 2L;

    private void setUpUserAndToken(User user, Token token) {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
    }

    @Test(expected = UserInfoExistsException.class)
    public void shouldThrowErrorWhenRegistrationUsernameExists() {
        given(userRepository.findByUsername("qwerty")).willReturn(Optional.of(user));
        UserRequestDto copycat = createUserRequestDto("notqwerty@qwerty.com", "qwerty");

        userServiceImpl.saveUser(copycat);
    }

    @Test(expected = UserInfoExistsException.class)
    public void shouldThrowErrorWhenRegistrationEmailExists() {
        given(userRepository.findByEmail("qwerty@qwerty.com")).willReturn(Optional.of(user));
        UserRequestDto copycat = createUserRequestDto("qwerty@qwerty.com", "notqwerty");

        userServiceImpl.saveUser(copycat);
    }

    @Test(expected = UserForbiddenException.class)
    public void shouldThrowErrorWhenTokenDoesNotMatchUserId() {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        UserPatchRequestDto update = createUserPatchRequestDto("newqwerty@newqwerty.com", "newqwerty");

        userServiceImpl.editUser(token.getToken(), 2L, update);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void shouldThrowErrorWhenInvalidUsernameFormat() {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        UserPatchRequestDto update = createUserPatchRequestDto("newqwerty@newqwerty.com", "wrong format");

        userServiceImpl.editUser(token.getToken(), userId, update);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void shouldThrowErrorWhenInvalidEmailFormat() {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        UserPatchRequestDto update = createUserPatchRequestDto("not an email", "newusername");

        userServiceImpl.editUser(token.getToken(), userId, update);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowErrorWhenUserToBeEditedDoesNotExist() {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        given(userRepository.findById(userId)).willReturn(Optional.empty());
        UserPatchRequestDto update = createUserPatchRequestDto("newqwerty@newqwerty.com", "newqwerty");

        userServiceImpl.editUser(token.getToken(), userId, update);
    }

    @Test(expected = UserInfoExistsException.class)
    public void shouldThrowErrorWhenNewUsernameExist() {
        setUpUserAndToken(user, token);
        UserPatchRequestDto update = createUserPatchRequestDto("newqwerty@newqwerty.com", "newqwerty");
        given(userRepository.findByUsername(update.getUsername())).willReturn(Optional.of(createUser("notqwerty@qwerty.com", "newqwerty")));

        userServiceImpl.editUser(token.getToken(), userId, update);
    }

    @Test(expected = UserInfoExistsException.class)
    public void shouldThrowErrorWhenNewEmailExists() {
        setUpUserAndToken(user, token);
        UserPatchRequestDto update = createUserPatchRequestDto("newqwerty@newqwerty.com", "newqwerty");
        given(userRepository.findByUsername(update.getUsername())).willReturn(Optional.empty());
        given(userRepository.findByEmail(update.getEmail())).willReturn(Optional.of(createUser("newqwerty@newqwerty.com", "notqwerty")));

        userServiceImpl.editUser(token.getToken(), userId, update);
    }

    @Test(expected = UserForbiddenException.class)
    public void shouldThrowErrorWhenRequestingManagerDoesNotMatchRequestedUser() {
        setUpUserAndToken(user, token);
        given(userRepository.findById(userId2)).willReturn(Optional.of(user2));
        given(userRepository.hasManagerEmployeeRelationship(userId2, userId, user.getManagerId())).willReturn(0);

        userServiceImpl.findUserById(userId2, token.getToken());
    }

    @Test
    public void shouldPassWhenRequestingUserDoesMatchRequestedUser() {
        setUpUserAndToken(user, token);
        given(userRepository.hasManagerEmployeeRelationship(userId, userId, user.getManagerId())).willReturn(0);

        UserResponseDto response = userServiceImpl.findUserById(userId, token.getToken());

        Assert.assertEquals(user.getId(), response.getId());
        Assert.assertEquals(user.getFirstName(), response.getFirstName());
    }

    @Test
    public void shouldReturnListOfUsersWhenManagerExists() {
        given(authServiceImpl.getTokenById(managerToken.getToken())).willReturn(managerToken);
        Long managerId = 3L;
        given(userRepository.findByManagerId(managerId)).willReturn((List.of(user2)));

        List<UserResponseDto> users = userServiceImpl.getUsers(managerToken.getToken(), managerId);

        Assert.assertEquals(users.size(), 1);
        assertThat(users).extracting(UserResponseDto::getId).containsExactlyInAnyOrder(2L);
    }

    @Test
    public void shouldReturnEmptyListWhenUserIsNotAManager() {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        given(userRepository.findByManagerId(userId)).willReturn(List.of());

        List<UserResponseDto> users = userServiceImpl.getUsers(token.getToken(), userId);

        Assert.assertEquals(users.size(), 0);
    }

    @Test(expected = UserForbiddenException.class)
    public void shouldThrowErrorWhenManagerTokenDoesNotMatch() {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);

        userServiceImpl.getUsers(token.getToken(), userId2);
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