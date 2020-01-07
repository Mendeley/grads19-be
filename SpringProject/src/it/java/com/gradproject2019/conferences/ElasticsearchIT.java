package com.gradproject2019.conferences;

import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.persistance.EsConference;
import com.gradproject2019.conferences.repository.ConferenceSearchRepository;
import com.gradproject2019.conferences.service.ConferenceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ElasticsearchIT {

    @LocalServerPort
    int testServerPort;
    private String baseUri;

    @Before
    public void setUp() {
        baseUri = "http://localhost:" + testServerPort + "/conferences";
    }

    @Autowired
    public TestRestTemplate restTemplate;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    ConferenceSearchRepository repository;

//    @Autowired
//    ElasticsearchTemplate template;

//    @Test
//    public void shouldSaveEsConferenceToSearchRepository() {
//        EsConference esConference = new EsConference();
//        esConference.setId(1L);
//        esConference.setName("SophiaCon");
//        //esConference.setDateTime(Instant.now());
//        esConference.setCity("London");
//        esConference.setDescription("A conference");
//        esConference.setTopic("Sophia");
//
//        EsConference savedConference = repository.save(esConference);
//
//        assertThat(savedConference).isSameAs(esConference);
//    }
//
//    @Test
//    public void testFindAll() {
//        Iterable<EsConference> esConference = repository.findAll();
//        Assert.assertTrue(esConference.iterator().hasNext());
//    }
//
//    @Test
//    public void testFindByTopic() {
//        Page<EsConference> esConference = repository.findByTopic("Sophia", PageRequest.of(1, 1));
//        Assert.assertTrue(esConference.getTotalPages() > 0);
//
//    }
//
//    @Test
//    public void shouldReturn200AndEmptyListWhenNoConferences() throws URISyntaxException {
//        URI uri = new URI(baseUri);
//
//        ResponseEntity<List<ConferenceResponseDto>> response = getConferenceList(uri);
//
//        Assert.assertEquals(200, response.getStatusCodeValue());
//        Assert.assertEquals(true, response.getBody().isEmpty());
//    }
//
//    @Test
//    public void shouldReturn200AndListOfConferencesWhenConferenceExists() throws URISyntaxException {
//        URI uri = new URI(baseUri);
//        Conference addedConference = conferenceRepository.saveAndFlush(conference);
//
//        ResponseEntity<List<ConferenceResponseDto>> response = getConferenceList(uri);
//
//        Assert.assertEquals(200, response.getStatusCodeValue());
//        Assert.assertEquals(conference.getName(), response.getBody().get(0).getName());
//        Assert.assertEquals(addedConference.getId(),response.getBody().get(0).getId());
//    }

    @Test
    public void testFindByTopic() throws URISyntaxException {
        EsConference esConference = new EsConference();
        esConference.setId(1L);
        esConference.setName("SophiaCon");
        //esConference.setDateTime(Instant.now());
        esConference.setCity("London");
        esConference.setDescription("A conference");
        esConference.setTopic("Sophia");

        EsConference esConference2 = new EsConference();
        esConference2.setId(2L);
        esConference2.setName("SophiaCon2");
        //esConference.setDateTime(Instant.now());
        esConference2.setCity("London2");
        esConference2.setDescription("A conference2");
        esConference2.setTopic("Sophia2");


        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        EsConference savedConference = repository.save(esConference);
        //EsConference savedConference2 = repository.save(esConference2);
        URI uri = new URI(baseUri + "/?topic=Sophia&page?from=0&size=1");
        //URI uri2 = new URI(baseUri );


        //List<ConferenceResponseDto> conferences = conferenceService.findByConferenceTopic("Sophia", 1, 1);

        ResponseEntity<ConferenceResponseDto> response = getEsConferenceList(uri);

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());
        //Assert.assertEquals(1, response.size());
        //Assert.assertEquals(esConference.getName(), response.getBody().get(0).getName());
    }
//
//    private ResponseEntity<List<ConferenceResponseDto>> getEsConferenceList(URI uri) {
//        return restTemplate.exchange(uri, GET, null, new ParameterizedTypeReference<List<ConferenceResponseDto>>() {});
//
//    }

    private ResponseEntity<ConferenceResponseDto> getEsConferenceList(URI uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<ConferenceResponseDto>() {});
    }

}

