package com.gradproject2019.conferences;

import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.persistance.EsConference;
import com.gradproject2019.conferences.repository.ConferenceSearchRepository;
import com.gradproject2019.conferences.service.ConferenceService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ElasticsearchIT {
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
    public void testFindByTopic() {
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
        //esConference2.setDateTime(Instant.now());
        esConference2.setCity("London2");
        esConference2.setDescription("A conference2");
        esConference2.setTopic("Sophia2");

        repository.save(esConference);
        repository.save(esConference2);

        List<ConferenceResponseDto> conferences = conferenceService.findByConferenceTopic("Sophia", 1, 1);

        Assert.assertNotNull(conferences);
        Assert.assertEquals(2, conferences.size());
    }


    private EsConference createConference(Long id, String name, String city, String description, String topic) {
        EsConference conference = new EsConference();
        conference.setId(id);
        conference.setName(name);
        conference.setCity(city);
        conference.setDescription(description);
        conference.setTopic(topic);
        return conference;
    }

//    private void recreateIndex() {
//        if (template.indexExists(EsConference.class)) {
//            template.deleteIndex(EsConference.class);
//            template.createIndex(EsConference.class);
//        }
//    }



}
