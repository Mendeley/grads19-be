package com.gradproject2019.user;

import com.gradproject2019.auth.persistance.Token;
import com.gradproject2019.auth.repository.AuthRepository;
import com.gradproject2019.users.data.UserPatchRequestDto;
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


    private String baseUrl;

    @Before
    public void setUp() {
        authRepository.deleteAll();
        userRepository.deleteAll();


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
        URI uri = new URI(baseUrl);
        UserRequestDto userRequestDto = createRequestDto("GracesCoolUsername", "Grace", "Burley Jones", "Grace@gmail.com", "P455w0rd!", "Botanist");
        HttpEntity<UserRequestDto> request = new HttpEntity<>(userRequestDto);

        ResponseEntity<String> response = postUser(uri, request);
        User retrievedUser = userRepository.findAll().get(0);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(userRequestDto.getUsername(), retrievedUser.getUsername());
    }

    @Test
    public void shouldReturn400WhenAnyFieldNull() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto( null, null, null, null, null, null));

        ResponseEntity<String> response = postUser(uri, request);

        Assert.assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenPasswordWrongFormat() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto("KaramsCoolUsername", "Karam", "Kapoor", "KSinghK@gmail.com", "wrong", "Botanist"));

        ResponseEntity<String> response = postUser(uri, request);

        Assert.assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenUsernameWrongFormat() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto("Karams Cool Username", "Karam", "Kapoor", "KSinghK@gmail.com", "wrong", "Botanist"));

        ResponseEntity<String> response = postUser(uri, request);

        Assert.assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn404WhenUserToBeEditedNotFound() throws URISyntaxException{
        URI uri = new URI(baseUrl + "/1000000000");
        HttpEntity<UserPatchRequestDto> request = new HttpEntity<>(createPatchRequestDto(null, null, null, null, null, null));

        ResponseEntity<UserResponseDto> response = editUser(uri, request);

        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn200AndEditOnlyNotNullFields() throws URISyntaxException {
        User user = new User ("Karams Cool Username", "Karam", "Kapoor", "KSinghK@gmail.com", "wrong", "Botanist");
        User savedUser = userRepository.saveAndFlush(user);
        URI uri = new URI(baseUrl + "/" + savedUser.getId());
        String newUsername= "sophiaUsername";
        String newFirstName= "Sophia";
        HttpEntity<UserPatchRequestDto> request = new HttpEntity<>(createPatchRequestDto(newUsername,newFirstName,null, null, null, null));

        ResponseEntity<UserResponseDto> response = editUser(uri, request);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getId(), response.getBody().getId());
        Assert.assertEquals(newUsername, response.getBody().getUsername());
        Assert.assertEquals(newFirstName, response.getBody().getFirstName());
        Assert.assertEquals(savedUser.getLastName(), response.getBody().getLastName());
        Assert.assertEquals(savedUser.getEmail(), response.getBody().getEmail());
        Assert.assertEquals(savedUser.getOccupation(), response.getBody().getOccupation());
    }

    @Test
    public void shouldReturn200WhenGettingUserById() throws URISyntaxException {
        User user = new User( "Grace", "Grace", "Jones", "gbj@gmail.com", "P455w0rd!", "na");
        User savedUser = userRepository.saveAndFlush(user);
        URI uri = new URI(baseUrl + "/" + savedUser.getId());
        Token testToken = new Token(savedUser.getId(), randomUUID());
        Token savedToken = authRepository.saveAndFlush(testToken);

        ResponseEntity<User> response = getUserById(uri, savedToken.getToken());

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getId(), response.getBody().getId());
        Assert.assertEquals(savedUser.getFirstName(), response.getBody().getFirstName());
        Assert.assertEquals(savedUser.getUsername(), response.getBody().getUsername());
        Assert.assertEquals(savedUser.getLastName(), response.getBody().getLastName());
        Assert.assertEquals(savedUser.getEmail(), response.getBody().getEmail());
        Assert.assertEquals(savedUser.getOccupation(), response.getBody().getOccupation());

    }

    @Test
    public void shouldReturn404WhenGettingUserByIDUnauthorised() throws URISyntaxException{
        User user = new User( "Grace", "Grace", "Jones", "gbj@gmail.com", "P455w0rd!", "na");
        User savedUser = userRepository.saveAndFlush(user);

        URI uri = new URI(baseUrl + "/" + savedUser.getId());

        ResponseEntity<User> response = getUserById(uri, randomUUID());

        Assert.assertEquals(404, response.getStatusCodeValue());
    }


    private ResponseEntity<String> postUser(URI uri, HttpEntity<UserRequestDto> request) {
        return restTemplate.exchange(uri, POST, request, new ParameterizedTypeReference<String>() {});
    }

    private ResponseEntity<UserResponseDto> editUser(URI uri, HttpEntity<UserPatchRequestDto> request) {
        return restTemplate.exchange(uri, PATCH, request, new ParameterizedTypeReference<UserResponseDto>() {});
    }

    private ResponseEntity<User> getUserById(URI uri, UUID token) {
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
