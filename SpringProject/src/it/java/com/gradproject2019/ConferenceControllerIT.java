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
    int testServerPort;

    @Before
    public void setUp() {
       conferenceRepository.deleteAll();
    }

    @After
    public void tearDown() {
       conferenceRepository.deleteAll();
    }

    @Test
    public void shouldReturn200AndEmptyListWhenDatabaseEmpty() throws URISyntaxException {
        //given
        final String baseUrl = "http://localhost:"+ testServerPort +"/conferences";
        URI uri = new URI(baseUrl);

        //when
        ResponseEntity<List<Conference>> response = this.restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Conference>>() {});

        //Then
        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertEquals(true,response.getBody().isEmpty());
    }

    @Test
    public void shouldReturn200AndListOfConferencesWhenDatabasePopulated() throws URISyntaxException {
        //given
        final String baseUrl = "http://localhost:"+ testServerPort +"/conferences";
        URI uri = new URI(baseUrl);
        Conference conference = new Conference(1L, "Grace's conference", Instant.now(), "Leicester", "All about Grace's fabulous and extra house", "grace");
        conferenceRepository.saveAndFlush(conference);

        //when
        ResponseEntity<List<Conference>> response = this.restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<Conference>>() {});

        //Then
        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertEquals(conference.getId(),response.getBody().get(0).getId());
        Assert.assertEquals(conference.getName(),response.getBody().get(0).getName());
    }

    @Test
    public void shouldReturn404WhenIdDoesNotExist() throws URISyntaxException {
        //given
        final String baseUrl = "http://localhost:"+ testServerPort +"/conferences/1000000000";
        URI uri = new URI(baseUrl);

        //when
        ResponseEntity<Conference> response = this.restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Conference>() {});

        //Then
        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn200AndConferenceWhenIdDoesExist() throws URISyntaxException {
        //given
        final String baseUrl = "http://localhost:"+ testServerPort +"/conferences/1";
        URI uri = new URI(baseUrl);
        Conference conference = new Conference(1L, "Grace's conference", Instant.now(), "Leicester", "All about Grace's fabulous and extra house", "grace");
        conferenceRepository.saveAndFlush(conference);

        //when
        ResponseEntity<Conference> response = this.restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Conference>() {});

        //Then
        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(conference.getId(), response.getBody().getId());
        Assert.assertEquals(conference.getName(), response.getBody().getName());
    }

    @Test
    public void shouldReturn200AndSaveConferenceInDatabase() throws URISyntaxException {
        //given
        final String baseUrl = "http://localhost:"+ testServerPort +"/conferences";
        URI uri = new URI(baseUrl);
        Conference conference = new Conference(1L, "Grace's conference", Instant.parse("3000-12-30T19:34:50.63Z"), "Leicester", "All about Grace's fabulous and extra house", "grace");
        HttpEntity<Conference> request = new HttpEntity<>(conference);

        //when
        ResponseEntity<String> response = this.restTemplate.exchange(uri, HttpMethod.POST, request, new ParameterizedTypeReference<String>() {});
        Conference retrievedConference = conferenceRepository.findById(1L).get();

        //Then
        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(conference.getId(), retrievedConference.getId());
        Assert.assertEquals(conference.getName(), retrievedConference.getName());
    }

    @Test
    public void shouldReturn409WhenConferenceIdAlreadyExists() throws URISyntaxException {
        //given
        final String baseUrl = "http://localhost:"+ testServerPort +"/conferences";
        URI uri = new URI(baseUrl);
        Conference conference1 = new Conference(1L, "Grace's conference2", Instant.now(), "Leicester2", "All about Grace's fabulous and extra house", "grace");
        Conference conference2 = new Conference(1L, "Sophia's conference", Instant.now(), "London", "All about Sophia's fabulous and extra house", "grace");
        HttpEntity<Conference> request = new HttpEntity<>(conference2);
        conferenceRepository.saveAndFlush(conference1);

        //when
        ResponseEntity<String> response = this.restTemplate.exchange(uri, HttpMethod.POST, request, new ParameterizedTypeReference<String>() {});

        //Then
        Assert.assertEquals(409,response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenAnyFieldNull() throws URISyntaxException {
        //given
        final String baseUrl = "http://localhost:"+ testServerPort +"/conferences";
        URI uri = new URI(baseUrl);
        Conference conference = new Conference(null, null, null, null, null, null);
        HttpEntity<Conference> request = new HttpEntity<>(conference);

        //when
        ResponseEntity<String> response = this.restTemplate.exchange(uri, HttpMethod.POST, request, new ParameterizedTypeReference<String>() {});

        //Then
        Assert.assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenConferenceInPast() throws URISyntaxException {
        //given
        final String baseUrl = "http://localhost:"+ testServerPort +"/conferences";
        URI uri = new URI(baseUrl);
        Conference conference = new Conference(1L, "Grace's conference2", Instant.parse("2018-12-30T19:34:50.63Z"), "Leicester2", "All about Grace's fabulous and extra house", "grace");
        HttpEntity<Conference> request = new HttpEntity<>(conference);

        //when
        ResponseEntity<String> response = this.restTemplate.exchange(uri, HttpMethod.POST, request, new ParameterizedTypeReference<String>() {});

        //Then
        Assert.assertEquals(400,response.getStatusCodeValue());
    }
}
