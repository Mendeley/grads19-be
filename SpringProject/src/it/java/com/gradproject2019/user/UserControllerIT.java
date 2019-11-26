package com.gradproject2019.user;

import com.gradproject2019.users.data.UserPatchRequestDto;
import com.gradproject2019.auth.persistance.Token;
import com.gradproject2019.auth.repository.AuthRepository;
import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.users.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpMethod.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int testServerPort;

    private User user;
    private String baseUrl;

    @Before
    public void setUp() {
        authRepository.deleteAll();
        userRepository.deleteAll();
        user = new User(1L, "KaramsCoolUsername", "Karam", "Kapoor", "KSinghK@gmail.com", "P455w0rd!", "Botanist");
        baseUrl = "http://localhost:" + testServerPort + "/users";
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @After
    public void tearDown() {
        authRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void shouldReturn200AndSaveUserInDatabase() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);
        UserRequestDto userRequestDto = createRequestDto("GracesCoolUsername", "Grace", "Burley Jones", "Grace@gmail.com", "P455w0rd!", "Botanist");
        HttpEntity<UserRequestDto> request = new HttpEntity<>(userRequestDto);

        //when
        ResponseEntity<String> response = postUser(uri, request);
        User retrievedUser = userRepository.findAll().get(0);

        //Then
        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(userRequestDto.getUsername(), retrievedUser.getUsername());
    }

    @Test
    public void shouldReturn400WhenAnyFieldNull() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto( null, null, null, null, null, null));

        //when
        ResponseEntity<String> response = postUser(uri, request);

        //Then
        Assert.assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenPasswordWrongFormat() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto("KaramsCoolUsername", "Karam", "Kapoor", "KSinghK@gmail.com", "wrong", "Botanist"));

        //when
        ResponseEntity<String> response = postUser(uri, request);

        //Then
        Assert.assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenUsernameWrongFormat() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto("Karams Cool Username", "Karam", "Kapoor", "KSinghK@gmail.com", "wrong", "Botanist"));

        //when
        ResponseEntity<String> response = postUser(uri, request);

        //Then
        Assert.assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn404WhenUserToBeEditedNotFound() throws URISyntaxException{
        //given
        URI uri = new URI(baseUrl + "/1000000000");
        HttpEntity<UserPatchRequestDto> request = new HttpEntity<>(createPatchRequestDto(null, null, null, null, null, null));

        //when
        ResponseEntity<UserResponseDto> response = editUser(uri, request);

        //then
        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn200AndEditOnlyNotNullFields() throws URISyntaxException {
        //given
        User savedUser = userRepository.saveAndFlush(user);
        URI uri = new URI(baseUrl + "/" + savedUser.getId());
        String newUsername= "sophiaUsername";
        String newFirstName= "Sophia";
        HttpEntity<UserPatchRequestDto> request = new HttpEntity<>(createPatchRequestDto(newUsername,newFirstName,null, null, null, null));

        //when
        ResponseEntity<UserResponseDto> response = editUser(uri, request);

        //then
        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getId(), response.getBody().getId());
        Assert.assertEquals(newUsername, response.getBody().getUsername());
        Assert.assertEquals(newFirstName, response.getBody().getFirstName());
        Assert.assertEquals(savedUser.getLastName(), response.getBody().getLastName());
        Assert.assertEquals(savedUser.getEmail(), response.getBody().getEmail());
        Assert.assertEquals(savedUser.getOccupation(), response.getBody().getOccupation());
    }

    //should pass if user in user database and token in token database
    @Test
    public void shouldReturn200WhenUserRegisteredandLoggedIn() throws URISyntaxException {


        User user = new User( "Grace", "Grace", "Jones", "gbj@gmail.com", "P455w0rd!", "na");
        User savedUser = userRepository.saveAndFlush(user);

        Token testToken = new Token(savedUser.getId(), randomUUID());
        Token savedToken = authRepository.saveAndFlush(testToken);

        //when the user tries to view their profile
        ResponseEntity<User> response = getUserById(savedUser.getId(), savedToken.getToken());

        //then
        Assert.assertEquals(200, response.getStatusCodeValue());
    }


    private ResponseEntity<String> postUser(URI uri, HttpEntity<UserRequestDto> request) {
        return restTemplate.exchange(uri, POST, request, new ParameterizedTypeReference<String>() {});
    }

    private ResponseEntity<UserResponseDto> editUser(URI uri, HttpEntity<UserPatchRequestDto> request) {
        return restTemplate.exchange(uri, PATCH, request, new ParameterizedTypeReference<UserResponseDto>() {});
    }

    private ResponseEntity<User> getUserById(Long userId, UUID token) throws URISyntaxException {
        URI uri = new URI(baseUrl + "/" + userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token.toString());
        return restTemplate.exchange(uri , GET, new HttpEntity<>(headers), User.class);
    }

    private UserRequestDto createRequestDto(String username, String firstName, String lastName, String email, String password, String occupation) {
        return UserRequestDto
                .UserRequestDtoBuilder
                .anUserRequestDto()
                .withUsername(username)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(password)
                .withOccupation(occupation)
                .build();
    }

    private UserPatchRequestDto createPatchRequestDto(String username, String firstName, String lastName, String email, String password, String occupation) {
        return UserPatchRequestDto
                .UserPatchRequestDtoBuilder
                .anUserPatchRequestDto()
                .withUsername(username)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withOccupation(occupation)
                .build();
    }
}
