package com.gradproject2019.auth;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.gradproject2019.utils.TestUtils;
import com.gradproject2019.auth.data.LoginDto;
import com.gradproject2019.auth.persistance.Token;
import com.gradproject2019.users.persistance.User;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIT extends TestUtils {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int testServerPort;

    private User user;
    private URI loginUri;
    private String baseUri;
    private String hashedPassword;
    private LoginDto loginDto;
    private URI logoutUri;
    private Token testToken;

    @Before
    public void setUp() throws URISyntaxException {
        clearRepositories();
        hashedPassword = PasswordUtils.hash("P455w0rd!");
        user = new User(1L, "KaramsCoolUsername", "Karam", "Kapoor", "KSinghK@gmail.com", hashedPassword, "Botanist");
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        loginDto = createLoginDto("KaramsCoolUsername", "P455w0rd!");

        baseUri = "http://localhost:" + testServerPort + "/auth";
        loginUri = new URI(baseUri + "/login");
        logoutUri = new URI(baseUri + "/logout");

        testToken = new Token(1L, randomUUID());
    }

    @After
    public void tearDown() {
        clearRepositories();
    }

    //tests to check login in endpoint works
    @Test
    public void shouldReturn200andCreateTokenWhenUserExistsAndPasswordCorrect() {
        //given
        User savedUser = userRepository.saveAndFlush(user);

        //when
        ResponseEntity<Token> response = login();

        //then
        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getId(), response.getBody().getUserId());
        Assert.assertNotNull(response.getBody().getToken());
    }

    @Test
    public void shouldReturn401WhenUserDoesNotExist() {
        //given
        HttpEntity<LoginDto> request = new HttpEntity<>(loginDto);

        //when
        ResponseEntity<ErrorEntity> response = loginExpectingError(request);

        //then
        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("Invalid user credentials.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn401WhenUserExistsAndPasswordIncorrect() {
        //given
        LoginDto loginWrongPasswordDto = createLoginDto("KaramsCoolUsername", "WrongPassword");
        HttpEntity<LoginDto> request = new HttpEntity<>(loginWrongPasswordDto);

        //when
        userRepository.saveAndFlush(user);
        ResponseEntity<ErrorEntity> response = loginExpectingError(request);

        //then
        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("Invalid user credentials.", response.getBody().getMessage());
    }

    //test to check the delete token endpoint
    @Test
    public void shouldReturn204andDeleteTokenWhenTokenExists() {
        //given token exists
        Token savedToken = authRepository.saveAndFlush(testToken);

        //when the user logs out
        ResponseEntity response = logout();

        //then
        Assert.assertEquals(204, response.getStatusCodeValue());

    }



    private ResponseEntity<Token> login() {
        return restTemplate.exchange(loginUri, POST,  new HttpEntity<>(loginDto), Token.class);
    }

    private ResponseEntity<ErrorEntity> loginExpectingError(HttpEntity<LoginDto> request) {
        return restTemplate.exchange(loginUri, POST, request, ErrorEntity.class);
    }

    private ResponseEntity logout() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, testToken.getToken().toString());
        return restTemplate.exchange(logoutUri, DELETE, new HttpEntity<>(headers), Void.class);
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