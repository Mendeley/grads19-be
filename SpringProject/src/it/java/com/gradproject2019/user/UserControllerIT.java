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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT extends TestUtils {

    @LocalServerPort
    int testServerPort;

    private String baseUri;
    private URI uri;
    private URI uri2;

    @Before
    public void setUp() throws URISyntaxException {
        universalSetUp();
        baseUri = "http://localhost:" + testServerPort + "/users";
        uri = new URI(baseUri);
        uri2 = new URI(baseUri + "/" + savedUser.getId());
    }

    @After
    public void tearDown() {
        clearRepositories();
    }

    @Test
    public void shouldReturn200AndSaveUserInDatabase() {
        UserRequestDto userRequestDto = createRequestDto("MichaelsCoolUsername", "Michael", "Merrett", "MGHMerrett@gmail.com", "P455w0rd!", "Frustrated", savedUser.getId());
        HttpEntity<UserRequestDto> request = new HttpEntity<>(userRequestDto);

        ResponseEntity<String> response = postUser(request);
        User retrievedUser = userRepository.findAll().get(1);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(userRequestDto.getUsername(), retrievedUser.getUsername());
    }

    @Test
    public void shouldReturn400WhenAnyRegistrationFieldNull() {
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto(null, null, null, null, null, null, null));

        ResponseEntity<String> response = postUser(request);

        Assert.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenRegistrationPasswordWrongFormat() {
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto("KaramsCoolUsername", "Karam", "Kapoor", "KSinghK@gmail.com", "wrong", "Botanist", savedUser.getId()));

        ResponseEntity<String> response = postUser(request);

        Assert.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenRegistrationUsernameWrongFormat() {
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto("Karams Cool Username", "Karam", "Kapoor", "KSinghK@gmail.com", "wrong", "Botanist", savedUser.getId()));

        ResponseEntity<String> response = postUser(request);

        Assert.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn404WhenManagerDoesNotExist() {
        clearRepositories();
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto("KaramsCoolUsername", "Karam", "Kapoor", "KSinghK@gmail.com", "P455w0rd!", "Botanist", 1L));

        ResponseEntity<ErrorEntity> response = postUserExpectingError(request);

        Assert.assertEquals(404, response.getStatusCodeValue());
        Assert.assertEquals("Manager not found.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn200AndEditOnlyNotNullFields() {
        String newUsername= "sophiaUsername";
        String newFirstName= "Sophia";
        UserPatchRequestDto request = createPatchRequestDto(newUsername, newFirstName, null, null, null, null);

        ResponseEntity<UserResponseDto> response = editUser(request);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getId(), response.getBody().getId());
        Assert.assertEquals(newUsername, response.getBody().getUsername());
        Assert.assertEquals(newFirstName, response.getBody().getFirstName());
        Assert.assertEquals(savedUser.getLastName(), response.getBody().getLastName());
        Assert.assertEquals(savedUser.getEmail(), response.getBody().getEmail());
        Assert.assertEquals(savedUser.getOccupation(), response.getBody().getOccupation());
    }

    @Test
    public void shouldReturn404WhenUpdatedManagerDoesNotExist() {
        UserPatchRequestDto request = createPatchRequestDto(null, null, null, null, null, savedUser.getId()+1);

        ResponseEntity<ErrorEntity> response = editUserExpectingError(request);

        Assert.assertEquals(404, response.getStatusCodeValue());
        Assert.assertEquals("Manager not found.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn200WhenGettingUserById() {
        ResponseEntity<User> response = getUserById();

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getId(), response.getBody().getId());
        Assert.assertEquals(savedUser.getFirstName(), response.getBody().getFirstName());
        Assert.assertEquals(savedUser.getUsername(), response.getBody().getUsername());
        Assert.assertEquals(savedUser.getLastName(), response.getBody().getLastName());
        Assert.assertEquals(savedUser.getEmail(), response.getBody().getEmail());
        Assert.assertEquals(savedUser.getOccupation(), response.getBody().getOccupation());
    }

    @Test
    public void shouldReturn401WhenGettingUserByIdUnauthorised() {
        ResponseEntity<ErrorEntity> response = getUserByIdExpectingAuthError();

        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("User unauthorized to perform action.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn401WhenUnauthorisedEdit() {
        UserPatchRequestDto request = createPatchRequestDto(null, null, null, null, null, null);

        ResponseEntity<ErrorEntity> response = editUserExpectingAuthError(request);

        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("User unauthorized to perform action.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn200andEmptyListWhenNoUsers() throws URISyntaxException {
        clearRepositories();
        URI uri = new URI(baseUri + "/search?query=qwerty");

        ResponseEntity<List<UserResponseDto>> response = searchByName(uri);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(true, response.getBody().isEmpty());
    }

    @Test
    public void shouldReturn200AndUsersMatchingFirstNameCharSequence() throws URISyntaxException {
        URI uri = new URI(baseUri + "/search?query=karam");

        ResponseEntity<List<UserResponseDto>> response = searchByName(uri);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getFirstName(), response.getBody().get(0).getFirstName());
        Assert.assertEquals(savedUser.getLastName(), response.getBody().get(0).getLastName());
        Assert.assertEquals(savedUser.getId(), response.getBody().get(0).getId());
        Assert.assertEquals(1, response.getBody().size());
    }

    @Test
    public void shouldReturnUsersMatchingLastNameCharSequence() throws URISyntaxException {
        URI uri = new URI(baseUri + "/search?query=kapoor");

        ResponseEntity<List<UserResponseDto>> response = searchByName(uri);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getFirstName(), response.getBody().get(0).getFirstName());
        Assert.assertEquals(savedUser.getLastName(), response.getBody().get(0).getLastName());
        Assert.assertEquals(savedUser.getId(), response.getBody().get(0).getId());
        Assert.assertEquals(1, response.getBody().size());
    }

    @Test
    public void shouldReturnMatchingUsersInAlphabeticalOrder() throws URISyntaxException {
        User userZ = userRepository.saveAndFlush(new User("KaramZ", "Karam", "ZKapoor", "KSinghZ@gmail.com", "Qwerty!1", "Florist", savedUser.getId()));
        User userA = userRepository.saveAndFlush(new User("KaramA", "Karam", "AKapoor", "KSinghA@gmail.com", "Qwerty!1", "Tailor", savedUser.getId()));

        URI uri = new URI(baseUri + "/search?query=karam");

        ResponseEntity<List<UserResponseDto>> response = searchByName(uri);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(3, response.getBody().size());
        Assert.assertEquals(userA.getLastName(), response.getBody().get(0).getLastName());
        Assert.assertEquals(savedUser.getLastName(), response.getBody().get(1).getLastName());
        Assert.assertEquals(userZ.getLastName(), response.getBody().get(2).getLastName());
    }

    @Test
    public void shouldReturnUsersMatchingFullName() throws URISyntaxException {
        URI uri = new URI(baseUri + "/search?query=karam%20kapoor");

        ResponseEntity<List<UserResponseDto>> response = searchByName(uri);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getFirstName(), response.getBody().get(0).getFirstName());
        Assert.assertEquals(savedUser.getLastName(), response.getBody().get(0).getLastName());
        Assert.assertEquals(savedUser.getId(), response.getBody().get(0).getId());
        Assert.assertEquals(1, response.getBody().size());
    }

    private ResponseEntity<String> postUser(HttpEntity<UserRequestDto> request) {
        return restTemplate.exchange(uri, POST, request, new ParameterizedTypeReference<String>() {});
    }

    private ResponseEntity<ErrorEntity> postUserExpectingError(HttpEntity<UserRequestDto> request) {
        return restTemplate.exchange(uri, POST, request, new ParameterizedTypeReference<ErrorEntity>() {});
    }

    private ResponseEntity<UserResponseDto> editUser(UserPatchRequestDto request) {
        return restTemplate.exchange(uri2, PATCH, new HttpEntity<>(request, passingHeaders), new ParameterizedTypeReference<UserResponseDto>() {});
    }

    private ResponseEntity<ErrorEntity> editUserExpectingError(UserPatchRequestDto request) {
        return restTemplate.exchange(uri2, PATCH, new HttpEntity<>(request, passingHeaders), new ParameterizedTypeReference<ErrorEntity>() {});
    }

    private ResponseEntity<ErrorEntity> editUserExpectingAuthError(UserPatchRequestDto request) {
        return restTemplate.exchange(uri2, PATCH, new HttpEntity<>(request, failingHeaders), ErrorEntity.class);
    }

    private ResponseEntity<User> getUserById() {
        return restTemplate.exchange(uri2 , GET, new HttpEntity<>(passingHeaders), User.class);
    }

    private ResponseEntity<ErrorEntity> getUserByIdExpectingAuthError() {
        return restTemplate.exchange(uri2 , GET, new HttpEntity<>(failingHeaders), ErrorEntity.class);
    }

    private ResponseEntity<List<UserResponseDto>> searchByName(URI uri) {
        return restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<List<UserResponseDto>>() {});
    }

    private UserRequestDto createRequestDto(String username, String firstName, String lastName, String email, String password, String occupation, Long managerId) {
        return UserRequestDto
                .UserRequestDtoBuilder
                .anUserRequestDto()
                .withUsername(username)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(password)
                .withOccupation(occupation)
                .withManagerId(managerId)
                .build();
    }

    private UserPatchRequestDto createPatchRequestDto(String username, String firstName, String lastName, String email, String occupation, Long managerId) {
        return UserPatchRequestDto
                .UserPatchRequestDtoBuilder
                .anUserPatchRequestDto()
                .withUsername(username)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withOccupation(occupation)
                .withManagerId(managerId)
                .build();
    }
}