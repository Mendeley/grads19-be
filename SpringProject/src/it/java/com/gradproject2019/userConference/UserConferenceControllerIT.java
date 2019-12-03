package com.gradproject2019.userConference;

import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.users.data.UserRequestDto;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.utils.TestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserConferenceControllerIT extends TestUtils {
    @LocalServerPort
    int testServerPort;

    private String baseUri;

    @Before
    public void setUp() {
        universalSetUp();
        baseUri = "http://localhost:" + testServerPort + "/";
    }

    @After
    public void tearDown() {
        clearRepositories();
    }

    @Test
    public void shouldReturn200AndSaveInterestInDatabase() throws URISyntaxException {
        URI uri = new URI(baseUri);
        UserConferenceRequestDto userConferenceRequestDto = createRequestDto();
        HttpEntity<UserConferenceRequestDto> request = new HttpEntity<>(userConferenceRequestDto);

        //ResponseEntity<String> response = saveInterest(uri, request);
        User retrievedUser = userConferenceRepository.findAll().get(1);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(userRequestDto.getUsername(), retrievedUser.getUsername());
    }


}
