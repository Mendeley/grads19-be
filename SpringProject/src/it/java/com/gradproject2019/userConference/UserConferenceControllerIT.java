package com.gradproject2019.userConference;

import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.persistence.UserConference;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserConferenceControllerIT extends TestUtils {

    @LocalServerPort
    int testServerPort;

    private URI baseUri;
    private URI baseDeleteUri;
    private UserConferenceRequestDto userConferenceRequestDto;

    @Before
    public void setUp() throws URISyntaxException {
        universalSetUp();
        baseUri = new URI("http://localhost:" + testServerPort + "/user-conferences");
        baseDeleteUri = new URI(baseUri.toString() + "/" + savedConference.getId());
        userConferenceRequestDto = new UserConferenceRequestDto(savedUser.getId(), savedConference.getId());
    }

    @After
    public void tearDown() {
        clearRepositories();
    }

    @Test
    public void shouldReturn200AndSaveInterestInConference() {
        userConferenceRepository.deleteAll();
        ResponseEntity<UserConferenceResponseDto> response = saveInterest(userConferenceRequestDto);
        UserConference retrievedUserConference = userConferenceRepository.findAll().get(0);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(userConferenceRequestDto.getUserId(), retrievedUserConference.getUserId());
        Assert.assertEquals(userConferenceRequestDto.getConferenceId(), retrievedUserConference.getConferenceId());
    }

    @Test
    public void shouldReturn401AndNotSaveWhenUserNotLoggedIn() {
        ResponseEntity<ErrorEntity> response = saveInterestExpectingAuthError(userConferenceRequestDto);

        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("User unauthorized to perform action.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn404AndNotSaveWhenConferenceDoesNotExist() {
        userConferenceRepository.deleteAll();
        conferenceRepository.deleteAll();
        ResponseEntity<ErrorEntity> response = saveInterestExpectingError(userConferenceRequestDto);

        Assert.assertEquals(404, response.getStatusCodeValue());
        Assert.assertEquals("Conference not found.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn403AndNotSaveWhenUserForbidden() {
        ResponseEntity<ErrorEntity> response = saveInterestExpectingError(new UserConferenceRequestDto(2L, savedConference.getId()));

        Assert.assertEquals(403, response.getStatusCodeValue());
        Assert.assertEquals("User forbidden to perform action.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn409AndNotSaveWhenConferenceAlreadyFavourited() {
        ResponseEntity<ErrorEntity> response = saveInterestExpectingError(userConferenceRequestDto);

        Assert.assertEquals(409, response.getStatusCodeValue());
        Assert.assertEquals("Conference already favourited.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn200AndListOfConferencesUserIsInterestedIn() {
        ResponseEntity<List<ConferenceResponseDto>> response = getConferencesByUserId();

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedConference.getName(), response.getBody().get(0).getName());
        Assert.assertEquals(savedConference.getId(), response.getBody().get(0).getId());
    }

    @Test
    public void shouldReturn200AndEmptyListWhenUserNotInterestedInConference() {
        userConferenceRepository.deleteAll();

        ResponseEntity<List<ConferenceResponseDto>> response = getConferencesByUserId();

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(true, response.getBody().isEmpty());
    }

    @Test
    public void shouldReturn401WhenUserRequestingConferenceNotLoggedIn() {
        ResponseEntity<ErrorEntity> response = getConferencesByUserIdExpectingAuthError();

        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("User unauthorized to perform action.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn204AndDeleteUserConference() {
        ResponseEntity response = deleteUserConferences();

        Assert.assertEquals(204, response.getStatusCodeValue());
        Assert.assertTrue(userConferenceRepository.findAll().isEmpty());
    }

    @Test
    public void shouldReturn401WhenUserRequestingDeleteNotLoggedIn() {
        ResponseEntity<ErrorEntity> response = deleteUserConferencesExpectingAuthError();

        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("User unauthorized to perform action.", response.getBody().getMessage());
    }

    @Test
    public void shouldReturn404WhenUserConferenceNotFound() throws URISyntaxException {
        URI uri = new URI(baseUri.toString() + "/" + Integer.MAX_VALUE);

        ResponseEntity<ErrorEntity> response = deleteUserConferencesExpectingError(uri);

        Assert.assertEquals(404, response.getStatusCodeValue());
        Assert.assertEquals("Conference has not been favourited yet.", response.getBody().getMessage());
    }

    private ResponseEntity<UserConferenceResponseDto> saveInterest(UserConferenceRequestDto request) {
        return restTemplate.exchange(baseUri, POST, new HttpEntity<>(request, passingHeaders), new ParameterizedTypeReference<UserConferenceResponseDto>() {});
    }

    private ResponseEntity<ErrorEntity> saveInterestExpectingAuthError(UserConferenceRequestDto request) {
        return restTemplate.exchange(baseUri, POST, new HttpEntity<>(request, failingHeaders), new ParameterizedTypeReference<ErrorEntity>() {});
    }

    private ResponseEntity<ErrorEntity> saveInterestExpectingError(UserConferenceRequestDto request) {
        return restTemplate.exchange(baseUri, POST, new HttpEntity<>(request, passingHeaders), new ParameterizedTypeReference<ErrorEntity>() {});
    }

    private ResponseEntity<List<ConferenceResponseDto>> getConferencesByUserId() {
        return restTemplate.exchange(baseUri, GET, new HttpEntity<>(passingHeaders), new ParameterizedTypeReference<List<ConferenceResponseDto>>() {});
    }

    private ResponseEntity<ErrorEntity> getConferencesByUserIdExpectingAuthError() {
        return restTemplate.exchange(baseUri, GET, new HttpEntity<>(failingHeaders), new ParameterizedTypeReference<ErrorEntity>() {});
    }

    private ResponseEntity deleteUserConferences() {
        return restTemplate.exchange(baseDeleteUri, DELETE, new HttpEntity<>(passingHeaders), Void.class);
    }

    private ResponseEntity<ErrorEntity> deleteUserConferencesExpectingAuthError() {
        return restTemplate.exchange(baseDeleteUri, DELETE, new HttpEntity<>(failingHeaders), new ParameterizedTypeReference<ErrorEntity>() {});
    }

    private ResponseEntity<ErrorEntity> deleteUserConferencesExpectingError(URI uri) {
        return restTemplate.exchange(uri, DELETE, new HttpEntity<>(passingHeaders), new ParameterizedTypeReference<ErrorEntity>() {});
    }
}