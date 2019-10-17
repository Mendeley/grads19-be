package com.gradproject2019;

import com.gradproject2019.conferences.persistance.Conference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
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
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int testServerPort = 8081;

    @Test
    public void testGetListOfConferencesReturnsAllConferences() throws URISyntaxException {
        //given
        final String baseUrl = "http://localhost:"+ testServerPort +"/conferences";
        URI uri = new URI(baseUrl);

        //when
        ResponseEntity<List> result = this.restTemplate.getForEntity(uri, List.class);

        //Then
        Assert.assertEquals(200,result.getStatusCodeValue());
    }

    @Test
    public void testGetListOfConferencesByIdReturnsAConference() throws URISyntaxException {
        //given
        final String baseUrl = "http://localhost:"+ testServerPort +"/conferences/100";
        URI uri = new URI(baseUrl);

        //when
        ResponseEntity<Conference> result = this.restTemplate.getForEntity(uri, Conference.class);

        //Then
        Assert.assertEquals(200,result.getStatusCodeValue());
    }

    @Test
    public void testAddConferenceSuccess() throws URISyntaxException {
        final String baseUrl = "http://localhost:"+ testServerPort +"/conferences";
        URI uri = new URI(baseUrl);
        Conference conference = new Conference(1L, "Grace's conference", Instant.now(), "Leicester", "All about Grace's fabulous and extra house", "grace");

        HttpEntity<Conference> request = new HttpEntity<>(conference);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        //Then
        Assert.assertEquals(200,result.getStatusCodeValue());
    }
}
