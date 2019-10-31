package com.gradproject2019;

import com.gradproject2019.conferences.data.ConferencePatchRequestDto;
import com.gradproject2019.conferences.data.ConferenceRequestDto;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

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

    @Before
    public void setUp() {
        conferenceRepository.deleteAll();
        conference = new Conference(1L, "Grace's conference", Instant.now().truncatedTo(ChronoUnit.SECONDS), "Leicester", "All about Grace's fabulous and extra house", "grace");
        baseUrl = "http://localhost:" + testServerPort + "/conferences";
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @After
    public void tearDown() {
       conferenceRepository.deleteAll();
    }

    @Test
    public void shouldReturn200AndEmptyListWhenDatabaseEmpty() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);

        //when
        ResponseEntity<List<ConferenceResponseDto>> response = getConferenceList(uri);

        //Then
        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertEquals(true,response.getBody().isEmpty());
    }

    @Test
    public void shouldReturn200AndListOfConferencesWhenDatabasePopulated() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);
        Conference addedConference = conferenceRepository.saveAndFlush(conference);

        //when
        ResponseEntity<List<ConferenceResponseDto>> response = getConferenceList(uri);

        //Then
        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertEquals(conference.getName(),response.getBody().get(0).getName());
        Assert.assertEquals(addedConference.getId(),response.getBody().get(0).getId());
    }

    @Test
    public void shouldReturn404WhenIdDoesNotExist() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl + "/1000000000");

        //when
        ResponseEntity<ConferenceResponseDto> response = getSingleConference(uri);

        //Then
        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn200AndConferenceWhenIdDoesExist() throws URISyntaxException {
        //given
        Conference addedConference =  conferenceRepository.saveAndFlush(conference);
        URI uri = new URI(baseUrl + "/" + addedConference.getId());

        //when
        ResponseEntity<ConferenceResponseDto> response = getSingleConference(uri);

        //Then

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(addedConference.getId(), response.getBody().getId());
        Assert.assertEquals(addedConference.getName(), response.getBody().getName());
    }


    @Test
    public void shouldReturn200AndSaveConferenceInDatabase() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);
        ConferenceRequestDto conferenceRequestDto = createRequestDto("Grace's conference", Instant.parse("3000-12-30T19:34:50.63Z"), "Leicester", "All about Grace's fabulous and extra house", "grace");
        HttpEntity<ConferenceRequestDto> request = new HttpEntity<>(conferenceRequestDto);

        //when
        ResponseEntity<String> response = postConference(uri, request);
        Conference retrievedConference = conferenceRepository.findAll().get(0);

        //Then
        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(conferenceRequestDto.getName(), retrievedConference.getName());
    }

    @Test
    public void shouldReturn400WhenAnyFieldNull() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);
        HttpEntity<ConferenceRequestDto> request = new HttpEntity<>(createRequestDto( null, null, null, null, null));

        //when
        ResponseEntity<String> response = postConference(uri, request);

        //Then
        Assert.assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenConferenceInPast() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);
        HttpEntity<ConferenceRequestDto> request = new HttpEntity<>(createRequestDto("Grace's conference", Instant.parse("2018-12-30T19:34:50.63Z"), "Leicester", "All about Grace's fabulous and extra house", "grace"));

        //when
        ResponseEntity<String> response = postConference(uri, request);

        //Then
        Assert.assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn404WhenConferenceToBeDeletedNotFound() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl + "/1000000000");

        //when
        ResponseEntity<String> response = deleteConference(uri);

        //then
        Assert.assertEquals(404,response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn204AndDeleteConference() throws URISyntaxException {
        //given
        Conference added = conferenceRepository.saveAndFlush(conference);
        URI uri = new URI(baseUrl + "/" + added.getId());

        //when
        ResponseEntity<String> response = deleteConference(uri);

        //then
        Assert.assertEquals(204,response.getStatusCodeValue());
        Assert.assertFalse(conferenceRepository.existsById(added.getId()));
    }

    @Test
    public void shouldReturn404WhenConferenceToBeEditedNotFound() throws URISyntaxException{
        //given
        URI uri = new URI(baseUrl + "/1000000000");
        HttpEntity<ConferencePatchRequestDto> request = new HttpEntity<>(createPatchRequestDto(null, null, null, null, null));

        //when
        ResponseEntity<ConferenceResponseDto> response = editConference(uri, request);

        //then
        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test   //WHEN CUSTOM HIBERNATE QUERY WRITTEN, THIS TEST WILL PASS
    public void shouldReturn200AndEditOnlyNotNullFields() throws URISyntaxException {
        //given
        Conference added = conferenceRepository.saveAndFlush(conference);
        URI uri = new URI(baseUrl + "/" + added.getId());
        added.setName("Michael's Conference");

        HttpEntity<ConferencePatchRequestDto> request = new HttpEntity<>(createPatchRequestDto(added.getName(),null, null, null, null));

        //when
        ResponseEntity<ConferenceResponseDto> response = editConference(uri, request);

        //then
        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertEquals(added.getId(), response.getBody().getId());
        Assert.assertEquals(added.getName(), response.getBody().getName());
        Assert.assertEquals(added.getDateTime(), response.getBody().getDateTime());
        Assert.assertEquals(added.getCity(), response.getBody().getCity());
        Assert.assertEquals(added.getDescription(), response.getBody().getDescription());
        Assert.assertEquals(added.getTopic(), response.getBody().getTopic());
    }



    private ResponseEntity<List<ConferenceResponseDto>> getConferenceList(URI uri) {
        return restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<List<ConferenceResponseDto>>() {});
    }

    private ResponseEntity<ConferenceResponseDto> getSingleConference(URI uri) {
        return restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<ConferenceResponseDto>() {});
    }

    private ResponseEntity<String> postConference(URI uri, HttpEntity<ConferenceRequestDto> request) {
        return restTemplate.exchange(uri, POST, request, new ParameterizedTypeReference<String>() {});
    }

    private ResponseEntity<String> deleteConference(URI uri) {
        return restTemplate.exchange(uri, DELETE, null, new ParameterizedTypeReference<String>() {});
    }

    private ResponseEntity<ConferenceResponseDto> editConference(URI uri, HttpEntity<ConferencePatchRequestDto> request) {
        return restTemplate.exchange(uri, PATCH, request, new ParameterizedTypeReference<ConferenceResponseDto>() {});
    }


    private ConferenceRequestDto createRequestDto(String name, Instant dateTime, String city, String description, String topic) {
        return ConferenceRequestDto
                .ConferenceRequestDtoBuilder
                .aConferenceRequestDto()
                .withName(name)
                .withDateTime(dateTime)
                .withCity(city)
                .withDescription(description)
                .withTopic(topic)
                .build();
    }
    private ConferencePatchRequestDto createPatchRequestDto(String name, Instant dateTime, String city, String description, String topic) {
        return ConferencePatchRequestDto
                .ConferencePatchRequestDtoBuilder
                .aConferencePatchRequestDto()
                .withName(name)
                .withDateTime(dateTime)
                .withCity(city)
                .withDescription(description)
                .withTopic(topic)
                .build();
    }
}
