package gradproject2019.elasticsearch.repository;

import gradproject2019.elasticsearch.persistence.EsConference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ConferenceSearchRepository extends ElasticsearchRepository<EsConference, String> {

//    Page<Conference> findByName(String name, Pageable pageable)
//
//    Page<Conference> findByCity(String city, Pageable pageable);
//
//    Page<Conference> findByDescription(String description, Pageable pageable);

     Page<EsConference> findByTopic(String topic, Pageable pageable);
}
