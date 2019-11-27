package com.gradproject2019.conferences;

import com.gradproject2019.conferences.data.ConferencePatchRequestDto;
import com.gradproject2019.conferences.data.ConferenceRequestDto;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.persistance.Conference;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpMethod.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConferenceControllerIT extends TestUtils {

    @LocalServerPort
    int testServerPort;

    private String baseUri;
    private Conference conference;

    @Before
    public void setUp() {
        universalSetUp();
        conference = new Conference(1L, "Grace's conference", Instant.now().truncatedTo(ChronoUnit.SECONDS), "Leicester", "All about Grace's fabulous and extra house", "grace");
        baseUri = "http://localhost:" + testServerPort + "/conferences";
    }

    @After
    public void tearDown() {
       clearRepositories();
    }

    @Test
    public void shouldReturn200AndEmptyListWhenDatabaseEmpty() throws URISyntaxException {
        URI uri = new URI(baseUri);

        ResponseEntity<List<ConferenceResponseDto>> response = getConferenceList(uri);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(true, response.getBody().isEmpty());
    }

    @Test
    public void shouldReturn200AndListOfConferencesWhenDatabasePopulated() throws URISyntaxException {
        URI uri = new URI(baseUri);
        Conference addedConference = conferenceRepository.saveAndFlush(conference);

        ResponseEntity<List<ConferenceResponseDto>> response = getConferenceList(uri);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(conference.getName(), response.getBody().get(0).getName());
        Assert.assertEquals(addedConference.getId(),response.getBody().get(0).getId());
    }

    @Test
    public void shouldReturn404WhenIdDoesNotExist() throws URISyntaxException {
        URI uri = new URI(baseUri + "/1000000000");

        ResponseEntity<ConferenceResponseDto> response = getSingleConference(uri);

        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn200AndConferenceWhenIdDoesExist() throws URISyntaxException {
        Conference addedConference =  conferenceRepository.saveAndFlush(conference);
        URI uri = new URI(baseUri + "/" + addedConference.getId());

        ResponseEntity<ConferenceResponseDto> response = getSingleConference(uri);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(addedConference.getId(), response.getBody().getId());
        Assert.assertEquals(addedConference.getName(), response.getBody().getName());
    }

    @Test
    public void shouldReturn200AndSaveConferenceInDatabase() throws URISyntaxException {
        URI uri = new URI(baseUri);
        ConferenceRequestDto conferenceRequestDto = createRequestDto("Grace's conference", Instant.parse("3000-12-30T19:34:50.63Z"), "Leicester", "All about Grace's fabulous and extra house", "grace");

        ResponseEntity<String> response = postConference(uri, conferenceRequestDto);
        Conference retrievedConference = conferenceRepository.findAll().get(0);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(conferenceRequestDto.getName(), retrievedConference.getName());
    }

    @Test
    public void shouldReturn400WhenAnyFieldNull() throws URISyntaxException {
        URI uri = new URI(baseUri);
        ConferenceRequestDto request = createRequestDto(null, null, null, null, null);

        ResponseEntity<String> response = postConference(uri, request);

        Assert.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenConferenceInPast() throws URISyntaxException {
        URI uri = new URI(baseUri);
        ConferenceRequestDto request = createRequestDto("Grace's conference", Instant.parse("2018-12-30T19:34:50.63Z"), "Leicester", "All about Grace's fabulous and extra house", "grace");

        ResponseEntity<String> response = postConference(uri, request);

        Assert.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn404WhenConferenceToBeDeletedNotFound() throws URISyntaxException {
        URI uri = new URI(baseUri + "/1000000000");

        ResponseEntity<String> response = deleteConference(uri);

        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn204AndDeleteConference() throws URISyntaxException {
        Conference savedConference = conferenceRepository.saveAndFlush(conference);
        URI uri = new URI(baseUri + "/" + savedConference.getId());

        ResponseEntity<String> response = deleteConference(uri);

        Assert.assertEquals(204, response.getStatusCodeValue());
        Assert.assertFalse(conferenceRepository.existsById(savedConference.getId()));
    }

    @Test
    public void shouldReturn401WhenUnauthorised() throws URISyntaxException {
        Conference savedConference = conferenceRepository.saveAndFlush(conference);
        URI uri = new URI(baseUri + "/" + savedConference.getId());

        ResponseEntity<ErrorEntity> response = deleteConferenceExpectingAuthError(uri);

        Assert.assertEquals(401, response.getStatusCodeValue());
        Assert.assertEquals("User unauthorized to perform action.", response.getBody().getMessage());
        Assert.assertTrue(conferenceRepository.existsById(savedConference.getId()));
    }

    @Test
    public void shouldReturn404WhenConferenceToBeEditedNotFound() throws URISyntaxException{
        URI uri = new URI(baseUri + "/1000000000");
        ConferencePatchRequestDto request = createPatchRequestDto(null, null, null, null, null);

        ResponseEntity<ConferenceResponseDto> response = editConference(uri, request);

        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void shouldReturn200AndEditOnlyNotNullFields() throws URISyntaxException {
        Conference savedConference = conferenceRepository.saveAndFlush(conference);
        URI uri = new URI(baseUri + "/" + savedConference.getId());
        String newName= "Harry's conference";
        String newCity= "Manchester/North";
        ConferencePatchRequestDto request = createPatchRequestDto(newName, null, newCity, null, null);

        ResponseEntity<ConferenceResponseDto> response = editConference(uri, request);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(savedConference.getId(), response.getBody().getId());
        Assert.assertEquals(newName, response.getBody().getName());
        Assert.assertEquals(savedConference.getDateTime(), response.getBody().getDateTime());
        Assert.assertEquals(newCity, response.getBody().getCity());
        Assert.assertEquals(savedConference.getDescription(), response.getBody().getDescription());
        Assert.assertEquals(savedConference.getTopic(), response.getBody().getTopic());
    }

    private ResponseEntity<List<ConferenceResponseDto>> getConferenceList(URI uri) {
        return restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<List<ConferenceResponseDto>>() {});
    }

    private ResponseEntity<ConferenceResponseDto> getSingleConference(URI uri) {
        return restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<ConferenceResponseDto>() {});
    }

    private ResponseEntity<String> postConference(URI uri, ConferenceRequestDto request) {
        return restTemplate.exchange(uri, POST, new HttpEntity<>(request, passingHeaders), new ParameterizedTypeReference<String>() {});
    }

    private ResponseEntity<String> deleteConference(URI uri) {
        return restTemplate.exchange(uri, DELETE, new HttpEntity<>(passingHeaders), new ParameterizedTypeReference<String>() {});
    }

    private ResponseEntity<ConferenceResponseDto> editConference(URI uri, ConferencePatchRequestDto request) {
        return restTemplate.exchange(uri, PATCH, new HttpEntity<>(request, passingHeaders), new ParameterizedTypeReference<ConferenceResponseDto>() {});
    }

    private ResponseEntity<ErrorEntity> deleteConferenceExpectingAuthError(URI uri) {
        return restTemplate.exchange(uri, DELETE, new HttpEntity<>(failingHeaders), ErrorEntity.class);
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