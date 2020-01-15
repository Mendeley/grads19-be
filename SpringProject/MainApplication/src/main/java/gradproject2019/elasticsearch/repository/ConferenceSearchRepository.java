package gradproject2019.elasticsearch.repository;

import gradproject2019.elasticsearch.persistence.EsConference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ConferenceSearchRepository extends ElasticsearchRepository<EsConference, String> {

    Page<EsConference> findByName(String name, Pageable pageable);

    Page<EsConference> findByCity(String city, Pageable pageable);

    Page<EsConference> findByDescription(String description, Pageable pageable);

    Page<EsConference> findByTopic(String topic, Pageable pageable);

}