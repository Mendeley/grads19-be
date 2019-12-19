package com.gradproject2019.conferences;

import com.gradproject2019.conferences.persistance.EsConference;
import com.gradproject2019.conferences.repository.ConferenceSearchRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ElasticsearchIT {

    @ClassRule
    public static ElasticsearchContainer container = new ElasticsearchContainer();
    @Autowired
    ConferenceSearchRepository repository;

    @BeforeClass
    public static void before() {
        System.setProperty("spring.data.elasticsearch.cluster-nodes", container.getContainerIpAddress() + ":" + container.getMappedPort(9300));
    }

    @Test
    public void shouldSaveEsConferenceToSearchRepository() {
        EsConference esConference = new EsConference();
        esConference.setId(1L);
        esConference.setName("SophiaCon");
        esConference.setDateTime(Instant.now());
        esConference.setCity("London");
        esConference.setDescription("A conference");
        esConference.setTopic("Sophia");
        esConference = repository.save(esConference);
        Assert.assertNotNull(esConference);

    }

}
