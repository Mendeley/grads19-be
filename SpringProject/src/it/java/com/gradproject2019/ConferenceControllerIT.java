package com.gradproject2019;

import com.gradproject2019.conferences.data.ConferenceRequestDto;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import org.junit.*;
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
        URI uri = new URI(baseUrl);

        //when
        ResponseEntity<List<ConferenceResponseDto>> responseList = getConferenceList(uri);

        //Then
        Assert.assertEquals(200,responseList.getStatusCodeValue());
        Assert.assertEquals(true,responseList.getBody().isEmpty());
    }

    @Test
    public void shouldReturn200AndListOfConferencesWhenDatabasePopulated() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);
        Conference addedConference = conferenceRepository.saveAndFlush(conference);

        //when
        ResponseEntity<List<ConferenceResponseDto>> responseList = getConferenceList(uri);

        //Then
        Assert.assertEquals(200,responseList.getStatusCodeValue());
        Assert.assertEquals(conference.getName(),responseList.getBody().get(0).getName());
        Assert.assertEquals(addedConference.getId(),responseList.getBody().get(0).getId());
    }

    @Test
    public void shouldReturn404WhenIdDoesNotExist() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl + "/1000000000");

        //when
        ResponseEntity<ConferenceResponseDto> responseConference = getSingleConference(uri);

        //Then
        Assert.assertEquals(404, responseConference.getStatusCodeValue());
    }

    @Test
    public void shouldReturn200AndConferenceWhenIdDoesExist() throws URISyntaxException {
        //given
        Conference addedConference =  conferenceRepository.saveAndFlush(conference);
        URI uri = new URI(baseUrl + "/" + addedConference.getId());

        //when
        ResponseEntity<ConferenceResponseDto> responseConference = getSingleConference(uri);

        //Then
        Assert.assertEquals(200, responseConference.getStatusCodeValue());
        Assert.assertEquals(addedConference.getId(), responseConference.getBody().getId());
        Assert.assertEquals(addedConference.getName(), responseConference.getBody().getName());
    }


    @Test
    public void shouldReturn200AndSaveConferenceInDatabase() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);
        ConferenceRequestDto conferenceRequestDto = createRequestDto(1L,"Grace's conference", Instant.parse("3000-12-30T19:34:50.63Z"), "Leicester", "All about Grace's fabulous and extra house", "grace");
        HttpEntity<ConferenceRequestDto> request = new HttpEntity<>(conferenceRequestDto);

        //when
        ResponseEntity<String> responseString = postConference(uri, request);
        Conference retrievedConference = conferenceRepository.findAll().get(0);

        //Then
        Assert.assertEquals(200, responseString.getStatusCodeValue());
        Assert.assertEquals(conferenceRequestDto.getName(), retrievedConference.getName());
    }

    @Test
    public void shouldReturn400WhenAnyFieldNull() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);
        HttpEntity<ConferenceRequestDto> request = new HttpEntity<>(createRequestDto(null, null, null, null, null, null));

        //when
        ResponseEntity<String> responseString = postConference(uri, request);

        //Then
        Assert.assertEquals(400,responseString.getStatusCodeValue());
    }

    @Test
    public void shouldReturn400WhenConferenceInPast() throws URISyntaxException {
        //given
        URI uri = new URI(baseUrl);
        HttpEntity<ConferenceRequestDto> request = new HttpEntity<>(createRequestDto(1L,"Grace's conference", Instant.parse("2018-12-30T19:34:50.63Z"), "Leicester", "All about Grace's fabulous and extra house", "grace"));

        //when
        ResponseEntity<String> responseString = postConference(uri, request);

        //Then
        Assert.assertEquals(400,responseString.getStatusCodeValue());
    }

    private ResponseEntity<List<ConferenceResponseDto>> getConferenceList(URI uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<ConferenceResponseDto>>() {});
    }

    private ResponseEntity<ConferenceResponseDto> getSingleConference(URI uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<ConferenceResponseDto>() {});
    }

    private ResponseEntity<String> postConference(URI uri, HttpEntity<ConferenceRequestDto> request) {
        return restTemplate.exchange(uri, HttpMethod.POST, request, new ParameterizedTypeReference<String>() {});
    }

    private ConferenceRequestDto createRequestDto(Long id, String name, Instant dateTime, String city, String description, String topic) {
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
}
