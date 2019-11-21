package com.gradproject2019;

import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.exception.CredentialsExistException;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.users.repository.UserRepository;
import com.gradproject2019.users.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private final User qwerty = new User( 3L, "qwerty", "qwerty", "qwerty", "qwerty@qwerty.com", "Qwerty!1", "qwerty");

    @Test(expected = CredentialsExistException.class)
    public void shouldThrowErrorWhenUsernameExists() {
        //given user adds username
        given(userRepository.findByUsername("qwerty")).willReturn(Optional.of(qwerty));
        UserRequestDto copycat = createUserRequestDto ("notqwerty@qwerty.com", "qwerty");

        //when username already exists
        userService.saveUser(copycat);
    }

    @Test(expected = CredentialsExistException.class)
    public void shouldThrowErrorWhenEmailExists() {
        //given user adds email
        given(userRepository.findByEmail("qwerty@qwerty.com")).willReturn(Optional.of(qwerty));
        UserRequestDto copycat = createUserRequestDto("qwerty@qwerty.com","notqwerty");

        //when email already exists
        userService.saveUser(copycat);
    }

    private UserRequestDto createUserRequestDto( String email, String username) {
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
}