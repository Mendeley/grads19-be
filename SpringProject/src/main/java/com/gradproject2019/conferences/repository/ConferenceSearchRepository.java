package com.gradproject2019.conferences.repository;

import com.gradproject2019.conferences.persistance.EsConference;
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
