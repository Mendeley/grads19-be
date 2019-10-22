package com.gradproject2019;

import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConferenceControllerIT {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int testServerPort = 8080;

    private Conference conference;
    private String baseUrl;
    private URI uri;
    private HttpEntity<Conference> request;
    private ResponseEntity<List<Conference>> responseList;
    private ResponseEntity<Conference> responseConference;
    private ResponseEntity<String> responseString;

    @Before
    public void setUp() {
        conferenceRepository.deleteAll();
        conference = new Conference(1L, "Grace's conference", Instant.now(), "Leicester", "All about Grace's fabulous and extra house", "grace");
        baseUrl = "http://localhost:" + testServerPort + "/conferences";
    }

    @After
    public void tearDown() {
       conferenceRepository.deleteAll();
    }

    @Test
    public void shouldReturn200AndEmptyListWhenDatabaseEmpty() throws URISyntaxException {
        //given
        uri = new URI(baseUrl);

        //when
        responseList = getConferenceList();

        //Then
        Assert.assertEquals(200,responseList.getStatusCodeValue());
        Assert.assertEquals(true,responseList.getBody().isEmpty());
    }

    @Test
    public void shouldReturn200AndListOfConferencesWhenDatabasePopulated() throws URISyntaxException {
        //given
        uri = new URI(baseUrl);
        conferenceRepository.saveAndFlush(conference);

        //when
        responseList = getConferenceList();

        //Then
        Assert.assertEquals(200,responseList.getStatusCodeValue());
        Assert.assertEquals(conference.getId(),responseList.getBody().get(0).getId());
        Assert.assertEquals(conference.getName(),responseList.getBody().get(0).getName());
    }

    @Test
    public void shouldReturn404WhenIdDoesNotExist() throws URISyntaxException {
        //given
        uri = new URI(baseUrl + "/1000000000");

        //when
        responseConference = getSingleConference();

        //Then
        Assert.assertEquals(404, responseConference.getStatusCodeValue());
    }

    @Test
    public void shouldReturn200AndConferenceWhenIdDoesExist() throws URISyntaxException {
        //given
        uri = new URI(baseUrl + "/1");
        conferenceRepository.saveAndFlush(conference);

        //when
        responseConference = getSingleConference();

        //Then
        Assert.assertEquals(200, responseConference.getStatusCodeValue());
        Assert.assertEquals(conference.getId(), responseConference.getBody().getId());
        Assert.assertEquals(conference.getName(), responseConference.getBody().getName());
    }

    @Test
    public void shouldReturn200AndSaveConferenceInDatabase() throws URISyntaxException {
        //given
        uri = new URI(baseUrl);
        Conference conference2 = new Conference(1L, "Grace's conference", Instant.parse("3000-12-30T19:34:50.63Z"), "Leicester", "All about Grace's fabulous and extra house", "grace");
        request = new HttpEntity<>(conference2);

        //when
        responseString = postConference();
        Conference retrievedConference = conferenceRepository.findById(1L).get();

        //Then
        Assert.assertEquals(200, responseString.getStatusCodeValue());
        Assert.assertEquals(conference2.getId(), retrievedConference.getId());
        Assert.assertEquals(conference2.getName(), retrievedConference.getName());
    }

    @Test
    public void shouldReturn409WhenConferenceIdAlreadyExists() throws URISyntaxException {
        //given
        uri = new URI(baseUrl);
        Conference conference2 = new Conference(1L, "Sophia's conference", Instant.now(), "London", "All about Sophia's fabulous and extra house", "grace");
        request = new HttpEntity<>(conference2);
        conferenceRepository.saveAndFlush(conference);

        //when
        responseString = postConference();

        //Then
        Assert.assertEquals(409,responseString.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenAnyFieldNull() throws URISyntaxException {
        //given
        uri = new URI(baseUrl);
        Conference conferenceNull = new Conference(null, null, null, null, null, null);
        request = new HttpEntity<>(conferenceNull);

        //when
        responseString = postConference();

        //Then
        Assert.assertEquals(400,responseString.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenConferenceInPast() throws URISyntaxException {
        //given
        uri = new URI(baseUrl);
        Conference conference2 = new Conference(1L, "Grace's conference2", Instant.parse("2018-12-30T19:34:50.63Z"), "Leicester2", "All about Grace's fabulous and extra house", "grace");
        request = new HttpEntity<>(conference2);

        //when
        responseString = postConference();

        //Then
        Assert.assertEquals(400,responseString.getStatusCodeValue());
    }

    private ResponseEntity<List<Conference>> getConferenceList() {
        return this.restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Conference>>() {});
    }

    private ResponseEntity<Conference> getSingleConference() {
        return this.restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Conference>() {});
    }

    private ResponseEntity<String> postConference() {
        return this.restTemplate.exchange(uri, HttpMethod.POST, request, new ParameterizedTypeReference<String>() {});
    }
}
