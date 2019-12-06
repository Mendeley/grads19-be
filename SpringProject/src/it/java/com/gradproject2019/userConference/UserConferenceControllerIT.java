package com.gradproject2019.userConference;

import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.persistance.UserConference;
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

import static com.gradproject2019.userConference.data.UserConferenceRequestDto.UserConferenceRequestDtoBuilder.anUserConferenceRequestDto;
import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserConferenceControllerIT extends TestUtils {
    @LocalServerPort
    int testServerPort;

    private String baseUri;

    @Before
    public void setUp() {
        universalSetUp();
        baseUri = "http://localhost:" + testServerPort + "/user-conferences";
    }

    @After
    public void tearDown() {
        clearRepositories();
    }

    @Test
    public void shouldReturn200AndSaveInterestInConference() throws URISyntaxException {
        URI uri = new URI(baseUri);
        UserConferenceRequestDto userConferenceRequestDto = createRequestDto(savedUser.getId(), savedConference.getId());

        ResponseEntity<UserConferenceResponseDto> response = saveInterest(uri, userConferenceRequestDto);
        UserConference retrievedUserConference = userConferenceRepository.findAll().get(0);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(userConferenceRequestDto.getUserId(), retrievedUserConference.getUserId());
        Assert.assertEquals(userConferenceRequestDto.getConferenceId(), retrievedUserConference.getConferenceId());
    }

    @Test
    public void shouldReturn401WhenUserNotLoggedIn() throws URISyntaxException {
        URI uri = new URI(baseUri);

        UserConferenceRequestDto userConferenceRequestDto = createRequestDto(savedUser.getId(), savedConference.getId());

        ResponseEntity<UserConferenceResponseDto> response = saveInterestExpectingAuthError(uri, userConferenceRequestDto);

        Assert.assertEquals(401, response.getStatusCodeValue());

    }

    private ResponseEntity<UserConferenceResponseDto> saveInterest(URI uri, UserConferenceRequestDto request) {
        return restTemplate.exchange(uri, POST, new HttpEntity<>(request, passingHeaders), new ParameterizedTypeReference<UserConferenceResponseDto>() {
        });
    }

    private ResponseEntity<UserConferenceResponseDto> saveInterestExpectingAuthError(URI uri, UserConferenceRequestDto request) {
        return restTemplate.exchange(uri, POST, new HttpEntity<>(request, failingHeaders), new ParameterizedTypeReference<UserConferenceResponseDto>() {
        });
    }

    private UserConferenceRequestDto createRequestDto(Long userId, Long conferenceId) {
        return anUserConferenceRequestDto()
                .withConferenceId(conferenceId)
                .withUserId(userId)
                .build();
    }
}