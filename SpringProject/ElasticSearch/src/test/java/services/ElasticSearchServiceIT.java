package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import grads19.batch.Conference;
import grads19.configs.AppConfig;
import grads19.services.ElasticSearchService;
import grads19.services.ElasticSearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static grads19.batch.Conference.ConferenceBuilder.aConference;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import({ElasticSearchServiceImpl.class, AppConfig.class, ObjectMapper.class})
@TestPropertySource(properties = {"elastic-search.endpoint=localhost", "elastic-search.protocol=http", "elastic-search.port=9200"})
class ElasticSearchServiceIT {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Test
    void shouldCreateIndices() {
        final var result = elasticSearchService.createIndices();

        assertThat(result).isEqualTo(true);
    }

    @Test
    void shouldInsertData() {

        Conference conf1 = aConference().withName("name1").withCity("city1").withDescription("description1").withTopic("topic1").build();
        Conference conf2 = aConference().withName("name2").withCity("city2").withDescription("description2").withTopic("topic2").build();

        List<Conference> conferences = List.of(conf1, conf2);

        final var result = elasticSearchService.insertData(conferences);

        assertThat(result).isEqualTo(true);
    }

    @Test
    void shouldDeleteIndices() {
        final var result = elasticSearchService.deleteIndices();

        assertThat(result).isEqualTo(true);
    }

}