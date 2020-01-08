package gradproject2019.user;

import gradproject2019.auth.persistence.Token;
import gradproject2019.users.data.UserPatchRequestDto;
import gradproject2019.users.data.UserRequestDto;
import gradproject2019.users.data.UserResponseDto;
import gradproject2019.users.persistence.User;
import gradproject2019.utils.ErrorEntity;
import gradproject2019.utils.TestUtils;
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
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpMethod.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT extends TestUtils {

    @LocalServerPort
    int testServerPort;

    private String baseUri;
    private URI userUri;
    private URI savedUserUri;
    private URI savedManagerUri;
    private URI savedUserWithoutManagerUri;

    @Before
    public void setUp() throws URISyntaxException {
        universalSetUp();
        baseUri = "http://localhost:" + testServerPort + "/users";
        userUri = new URI(baseUri);
        savedUserUri = new URI(baseUri + "/" + savedUser.getId());
        savedManagerUri = new URI(baseUri  +"?manager_id=" + savedManager.getId());
        savedUserWithoutManagerUri = new URI(baseUri + "?manager_id=" + savedUser.getId());

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
        User retrievedUser = userRepository.findAll().get(3);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(userRequestDto.getUsername(), retrievedUser.getUsername());
    }

    @Test
    public void shouldReturn400WhenAnyRegistrationFieldNull() {
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto(null, null, null, null, null, null, null));

        ResponseEntity<ErrorEntity> response = postUserExpectingError(request);

        Assert.assertEquals(400, response.getStatusCodeValue());
        Assert.assertEquals("Validation failed for object='userRequestDto'. Error count: 6", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn400WhenRegistrationPasswordWrongFormat() {
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto("KaramsCoolUsername", "Karam", "Kapoor", "KSinghK@gmail.com", "wrong", "Botanist", savedUser.getId()));

        ResponseEntity<ErrorEntity> response = postUserExpectingError(request);

        Assert.assertEquals(400, response.getStatusCodeValue());
        Assert.assertEquals("Invalid user credentials.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn400WhenRegistrationUsernameWrongFormat() {
        HttpEntity<UserRequestDto> request = new HttpEntity<>(createRequestDto("Karams Cool Username", "Karam", "Kapoor", "KSinghK@gmail.com", "wrong", "Botanist", savedUser.getId()));

        ResponseEntity<ErrorEntity> response = postUserExpectingError(request);

        Assert.assertEquals(400, response.getStatusCodeValue());
        Assert.assertEquals("Invalid user credentials.", response.getBody().getMessage());
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
        String newUsername = "sophiaUsername";
        String newFirstName = "Sophia";
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
        UserPatchRequestDto request = createPatchRequestDto(null, null, null, null, null, Long.MAX_VALUE);

        ResponseEntity<ErrorEntity> response = editUserExpectingError(request, passingHeaders);

        Assert.assertEquals(404, response.getStatusCodeValue());
        Assert.assertEquals("Manager not found.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn200WhenGettingUserByOwnId() {
        ResponseEntity<User> response = getUserById(savedUserUri, passingHeaders);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getId(), response.getBody().getId());
        Assert.assertEquals(savedUser.getFirstName(), response.getBody().getFirstName());
        Assert.assertEquals(savedUser.getUsername(), response.getBody().getUsername());
        Assert.assertEquals(savedUser.getLastName(), response.getBody().getLastName());
        Assert.assertEquals(savedUser.getEmail(), response.getBody().getEmail());
        Assert.assertEquals(savedUser.getOccupation(), response.getBody().getOccupation());
    }

    @Test
    public void shouldReturn200WhenGettingManagerById() {
        User employee = userRepository.saveAndFlush(new User("employee", "employee", "employee", "employee@email.com", "Password!1", "employee", savedUser.getId()));
        Token employeeToken = authRepository.saveAndFlush(new Token(employee.getId(), UUID.randomUUID()));
        HttpHeaders employeeHeaders = new HttpHeaders();
        employeeHeaders.add(HttpHeaders.AUTHORIZATION, employeeToken.getToken().toString());

        ResponseEntity<User> response = getUserById(savedUserUri, employeeHeaders);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedUser.getId(), response.getBody().getId());
        Assert.assertEquals(savedUser.getFirstName(), response.getBody().getFirstName());
        Assert.assertEquals(savedUser.getUsername(), response.getBody().getUsername());
        Assert.assertEquals(savedUser.getLastName(), response.getBody().getLastName());
        Assert.assertEquals(savedUser.getEmail(), response.getBody().getEmail());
        Assert.assertEquals(savedUser.getOccupation(), response.getBody().getOccupation());
    }

    @Test
    public void shouldReturn200WhenGettingEmployeeById() throws URISyntaxException {
        User employee = userRepository.saveAndFlush(new User("employee", "employee", "employee", "employee@email.com", "Password!1", "employee", savedUser.getId()));
        URI employeeUri = new URI(baseUri + "/" + employee.getId());

        ResponseEntity<User> response = getUserById(employeeUri, passingHeaders);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(employee.getId(), response.getBody().getId());
    }

    @Test
    public void shouldReturn200WhenGettingEmployeesByManagerId() {
        ResponseEntity<List<UserResponseDto>> response = getUserByManagerId(savedManagerUri, managerPassingHeaders);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(response.getBody().size(), 1);
        Assert.assertEquals(savedUserWithManager.getId(), response.getBody().get(0).getId());
    }

    @Test
    public void shouldReturn401WhenUserUnauthorisedToGetEmployees() {
        ResponseEntity<ErrorEntity> response = getUserByManagerIdExpectingError(savedManagerUri, failingHeaders);

        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("User unauthorized to perform action.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturnEmptyListWhenUserHasNoEmployees() {

        ResponseEntity<List<UserResponseDto>> response = getUserByManagerId(savedUserWithoutManagerUri, passingHeaders);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(0, response.getBody().size());
    }

    @Test
    public void shouldReturn401WhenGettingUserByIdUnauthorised() {
        ResponseEntity<ErrorEntity> response = getUserByIdExpectingError(savedUserUri, failingHeaders);

        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("User unauthorized to perform action.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn403WhenGettingUserWhoIsNotTheUserOrTheirManagerOrTheirEmployee() throws URISyntaxException {
        User unconnectedUser = userRepository.saveAndFlush(new User("unconnected", "unconnected", "unconnected", "unconnected@email.com", "Password!1", "unconnected", null));
        URI unconnectedUri = new URI(baseUri + "/" + unconnectedUser.getId());

        ResponseEntity<ErrorEntity> response = getUserByIdExpectingError(unconnectedUri, passingHeaders);

        Assert.assertEquals(403, response.getStatusCodeValue());
        Assert.assertEquals("User forbidden to perform action.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn401WhenUnauthorisedEdit() {
        UserPatchRequestDto request = createPatchRequestDto(null, null, null, null, null, null);

        ResponseEntity<ErrorEntity> response = editUserExpectingError(request, failingHeaders);

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
        return restTemplate.exchange(userUri, POST, request, new ParameterizedTypeReference<String>() {
        });
    }

    private ResponseEntity<ErrorEntity> postUserExpectingError(HttpEntity<UserRequestDto> request) {
        return restTemplate.exchange(userUri, POST, request, new ParameterizedTypeReference<ErrorEntity>() {
        });
    }

    private ResponseEntity<UserResponseDto> editUser(UserPatchRequestDto request) {
        return restTemplate.exchange(savedUserUri, PATCH, new HttpEntity<>(request, passingHeaders), new ParameterizedTypeReference<UserResponseDto>() {
        });
    }

    private ResponseEntity<ErrorEntity> editUserExpectingError(UserPatchRequestDto request, HttpHeaders headers) {
        return restTemplate.exchange(savedUserUri, PATCH, new HttpEntity<>(request, headers), new ParameterizedTypeReference<ErrorEntity>() {
        });
    }

    private ResponseEntity<User> getUserById(URI uri, HttpHeaders headers) {
        return restTemplate.exchange(uri, GET, new HttpEntity<>(headers), User.class);
    }

    private ResponseEntity<ErrorEntity> getUserByIdExpectingError(URI uri, HttpHeaders headers) {
        return restTemplate.exchange(uri, GET, new HttpEntity<>(headers), ErrorEntity.class);
    }

    private ResponseEntity<List<UserResponseDto>> searchByName(URI uri) {
        return restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<List<UserResponseDto>>() {
        });
    }

    private ResponseEntity<List<UserResponseDto>> getUserByManagerId(URI uri, HttpHeaders headers) {
        return restTemplate.exchange(uri, GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<UserResponseDto>>() {
        });
    }

    private ResponseEntity<ErrorEntity> getUserByManagerIdExpectingError(URI uri, HttpHeaders headers) {
        return restTemplate.exchange(uri, GET, new HttpEntity<>(headers), new ParameterizedTypeReference<ErrorEntity>() {
        });
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