package com.gradproject2019;

import com.gradproject2019.auth.data.LoginDto;
import com.gradproject2019.auth.persistance.Token;
import com.gradproject2019.auth.repository.AuthRepository;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.users.repository.UserRepository;
import com.gradproject2019.utils.PasswordUtils;
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
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIT {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int testServerPort = 8080;

    private User user;
    private String realPassword;
    private String baseUrl;

    @Before
    public void setUp() {
        authRepository.deleteAll();
        userRepository.deleteAll();
        realPassword = PasswordUtils.hash("P455w0rd!");
        user = new User(1L, "KaramsCoolUsername", "Karam", "Kapoor", "KSinghK@gmail.com", realPassword, "Botanist");
        baseUrl = "http://localhost:" + testServerPort + "/auth";
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @After
    public void tearDown() {
        authRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void shouldReturn200andCreateTokenWhenUserExistsAndPasswordCorrect() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl +"/login");
        LoginDto loginDto = createLoginDto("KaramsCoolUsername", "P455w0rd!");
        HttpEntity<LoginDto> request = new HttpEntity<>(loginDto);

        //when
        User savedUser = userRepository.saveAndFlush(user);
        ResponseEntity<Token> response = login(uri, request);

        //then
        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getId(), response.getBody().getUserId());
    }

    @Test
    public void shouldReturn404WhenUserDoesNotExists() throws URISyntaxException{
        //given
        URI uri = new URI(baseUrl +"/login");
        LoginDto loginDto = createLoginDto("KaramsCoolUsername", "P455w0rd!");
        HttpEntity<LoginDto> request = new HttpEntity<>(loginDto);

        //when
        ResponseEntity<Token> response = login(uri, request);

        //then
        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn403nWhenUserExistsAndPasswordIncorrect() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl +"/login");
        LoginDto loginDto = createLoginDto("KaramsCoolUsername", "WrongPassword");
        HttpEntity<LoginDto> request = new HttpEntity<>(loginDto);

        //when
        userRepository.saveAndFlush(user);
        ResponseEntity<Token> response = login(uri, request);

        //then
        Assert.assertEquals(403, response.getStatusCodeValue());
    }

    private ResponseEntity<Token> login(URI uri, HttpEntity<LoginDto> request) {
        return restTemplate.exchange(uri, POST, request, new ParameterizedTypeReference<Token>() {});
    }

    private LoginDto createLoginDto(String username, String password) {
        return LoginDto
                .LoginDtoBuilder
                .aLoginDto()
                .withPassword(password)
                .withUsername(username)
                .build();
    }
}