package com.gradproject2019.userConference;

import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.persistence.UserConference;
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

import static com.gradproject2019.userConference.data.UserConferenceRequestDto.UserConferenceRequestDtoBuilder.anUserConferenceRequestDto;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserConferenceControllerIT extends TestUtils {
    @LocalServerPort
    int testServerPort;

    private URI baseUri;

    @Before
    public void setUp() throws URISyntaxException {
        universalSetUp();
        baseUri = new URI("http://localhost:" + testServerPort + "/user-conferences");
    }

    @After
    public void tearDown() {
        clearRepositories();
    }

    @Test
    public void shouldReturn200AndSaveInterestInConference() {
        UserConferenceRequestDto userConferenceRequestDto = createRequestDto(savedUser.getId(), savedConference.getId());

        ResponseEntity<UserConferenceResponseDto> response = saveInterest(baseUri, userConferenceRequestDto);
        UserConference retrievedUserConference = userConferenceRepository.findAll().get(0);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(userConferenceRequestDto.getUserId(), retrievedUserConference.getUserId());
        Assert.assertEquals(userConferenceRequestDto.getConferenceId(), retrievedUserConference.getConferenceId());
    }

    @Test
    public void shouldReturn401AndNotSaveWhenUserNotLoggedIn() {
        UserConferenceRequestDto userConferenceRequestDto = createRequestDto(savedUser.getId(), savedConference.getId());

        ResponseEntity<UserConferenceResponseDto> response = saveInterestExpectingAuthError(baseUri, userConferenceRequestDto);

        Assert.assertEquals(401, response.getStatusCodeValue());

    }

    @Test
    public void shouldReturn200AndListOfConferencesUserIsInterestedIn() throws URISyntaxException {
        URI uri = new URI (baseUri + "/" + savedConference.getId());
       // UserConferenceRequestDto userConferenceRequestDto = createRequestDto(savedUser.getId(), savedConference.getId());

        //ResponseEntity<List<ConferenceResponseDto>> response = getConferenceByUserId(uri);

        //Assert.assertEquals(200, response.getStatusCodeValue());
        //Assert.assertEquals(savedConference.getId(), response.getBody().getId());
       // Assert.assertEquals(savedConference.getName(), response.getBody().getName());
    }

    @Test
    public void shouldReturn401WhenUserNotLoggedIn() throws URISyntaxException {
        URI uri = new URI (baseUri + "/" + savedConference.getId());
        UserConferenceRequestDto userConferenceRequestDto = createRequestDto(savedUser.getId(), savedConference.getId());

        ResponseEntity<ConferenceResponseDto> response = getConferenceByUserIdExpectingAuthError(uri);

        Assert.assertEquals(401, response.getStatusCodeValue());

    }



    private ResponseEntity<UserConferenceResponseDto> saveInterest(URI uri, UserConferenceRequestDto request) {
        return restTemplate.exchange(uri, POST, new HttpEntity<>(request, passingHeaders), new ParameterizedTypeReference<>() {
        });
    }

    private ResponseEntity<UserConferenceResponseDto> saveInterestExpectingAuthError(URI uri, UserConferenceRequestDto request) {
        return restTemplate.exchange(uri, POST, new HttpEntity<>(request, failingHeaders), new ParameterizedTypeReference<>() {
        });
    }

    private ResponseEntity<List<ConferenceResponseDto>> getConferenceByUserId(URI uri) {
        return restTemplate.exchange(uri, GET, new HttpEntity<>(passingHeaders), new ParameterizedTypeReference<>() {
        });
    }

    private ResponseEntity<ConferenceResponseDto> getConferenceByUserIdExpectingAuthError(URI uri) {
        return restTemplate.exchange(uri, POST, new HttpEntity<>(failingHeaders), new ParameterizedTypeReference<>() {
        });
    }

    private UserConferenceRequestDto createRequestDto(Long userId, Long conferenceId) {
        return anUserConferenceRequestDto()
                .withConferenceId(conferenceId)
                .withUserId(userId)
                .build();
    }
}