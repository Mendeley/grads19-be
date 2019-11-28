package com.gradproject2019.user;

import com.gradproject2019.users.data.UserPatchRequestDto;
import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.data.UserResponseDto;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.utils.ErrorEntity;
import com.gradproject2019.utils.TestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.springframework.http.HttpMethod.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT extends TestUtils {

    @LocalServerPort
    int testServerPort;

    private String baseUri;

    @Before
    public void setUp() {
        universalSetUp();
        baseUri = "http://localhost:" + testServerPort + "/users";
    }

    @After
    public void tearDown() {
        clearRepositories();
    }

    @Test
    public void shouldReturn200AndSaveUserInDatabase() throws URISyntaxException {
        URI uri = new URI(baseUri);
        UserRequestDto userRequestDto = createRequestDto("MichaelsCoolUsername", "Michael", "Merrett", "MGHMerrett@gmail.com", "P455w0rd!", "Frustrated");
        HttpEntity<UserRequestDto> request = new HttpEntity<>(userRequestDto);

        ResponseEntity<String> response = postUser(uri, request);
        User retrievedUser = userRepository.findAll().get(1);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(userRequestDto.getUsername(), retrievedUser.getUsername());
    }

    @Test
    public void shouldReturn400WhenAnyFieldNull() throws URISyntaxException {
        URI uri = new URI(baseUri);
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto(null, null, null, null, null, null));

        ResponseEntity<String> response = postUser(uri, request);

        Assert.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenPasswordWrongFormat() throws URISyntaxException {
        URI uri = new URI(baseUri);
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto("KaramsCoolUsername", "Karam", "Kapoor", "KSinghK@gmail.com", "wrong", "Botanist"));

        ResponseEntity<String> response = postUser(uri, request);

        Assert.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenUsernameWrongFormat() throws URISyntaxException {
        URI uri = new URI(baseUri);
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto("Karams Cool Username", "Karam", "Kapoor", "KSinghK@gmail.com", "wrong", "Botanist"));

        ResponseEntity<String> response = postUser(uri, request);

        Assert.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn200AndEditOnlyNotNullFields() throws URISyntaxException {
        URI uri = new URI(baseUri + "/" + savedUser.getId());
        String newUsername= "sophiaUsername";
        String newFirstName= "Sophia";
        UserPatchRequestDto request = createPatchRequestDto(newUsername, newFirstName, null, null, null);

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
    public void shouldReturn401WhenUserUnauthorised() throws URISyntaxException {
        URI uri = new URI(baseUri + "/" + savedUser.getId());
        UserPatchRequestDto request = createPatchRequestDto(null, null, null, null, null);

        ResponseEntity<ErrorEntity> response = editUserExpectingAuthError(uri, request);

        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("User unauthorized to perform action.", response.getBody().getMessage());
    }

    private ResponseEntity<String> postUser(URI uri, HttpEntity<UserRequestDto> request) {
        return restTemplate.exchange(uri, POST, request, new ParameterizedTypeReference<String>() {});
    }

    private ResponseEntity<UserResponseDto> editUser(URI uri, UserPatchRequestDto request) {
        return restTemplate.exchange(uri, PATCH, new HttpEntity<>(request, passingHeaders), new ParameterizedTypeReference<UserResponseDto>() {});
    }

    private ResponseEntity<ErrorEntity> editUserExpectingAuthError(URI uri, UserPatchRequestDto request) {
        return restTemplate.exchange(uri, PATCH, new HttpEntity<>(request, failingHeaders), ErrorEntity.class);
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

    private UserPatchRequestDto createPatchRequestDto(String username, String firstName, String lastName, String email, String occupation) {
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