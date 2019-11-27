package com.gradproject2019.auth;

import com.gradproject2019.auth.data.LoginDto;
import com.gradproject2019.auth.persistance.Token;
import com.gradproject2019.utils.ErrorEntity;
import com.gradproject2019.utils.TestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIT extends TestUtils {

    @LocalServerPort
    int testServerPort;

    private String baseUri;
    private URI loginUri;
    private URI logoutUri;
    private LoginDto loginDto;
    private LoginDto secondLoginDto;

    @Before
    public void setUp() throws URISyntaxException {
        universalSetUp();
        loginDto = createLoginDto("KaramsCoolUsername", "P455w0rd!");
        secondLoginDto = createLoginDto("Test1", "Test123?");
        baseUri = "http://localhost:" + testServerPort + "/auth";
        loginUri = new URI(baseUri + "/login");
        logoutUri = new URI(baseUri + "/logout");
    }

    @After
    public void tearDown() {
        clearRepositories();
    }

    @Test
    public void shouldReturn200andCreateTokenWhenUserExistsAndPasswordCorrect() {
        ResponseEntity<Token> response = login();

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getId(), response.getBody().getUserId());
        Assert.assertNotNull(response.getBody().getToken());
    }

    @Test
    public void shouldReturn401WhenUserDoesNotExist() {
        HttpEntity<LoginDto> request = new HttpEntity<>(secondLoginDto);

        ResponseEntity<ErrorEntity> response = loginExpectingError(request);

        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("Invalid user credentials.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn401WhenUserExistsAndPasswordIncorrect() {
        LoginDto loginWrongPasswordDto = createLoginDto("KaramsCoolUsername", "WrongPassword");
        HttpEntity<LoginDto> request = new HttpEntity<>(loginWrongPasswordDto);

        ResponseEntity<ErrorEntity> response = loginExpectingError(request);

        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("Invalid user credentials.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn204andDeleteTokenWhenTokenExists() {
        ResponseEntity response = logout();

        Assert.assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn404WhenTokenDoesNotExist() {
        ResponseEntity response = logoutExpectingAuthError();

        Assert.assertEquals(404, response.getStatusCodeValue());
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

    private ResponseEntity logoutExpectingAuthError() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString());
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