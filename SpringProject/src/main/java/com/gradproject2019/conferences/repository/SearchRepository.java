package com.gradproject2019.conferences.repository;

import com.gradproject2019.conferences.persistance.Conference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface SearchRepository extends ElasticsearchRepository<Conference, String> {

    Page<Conference> findByConferenceName(String name, Pageable pageable)

    Page<Conference> findByConferenceCity(String city, Pageable pageable);

    Page<Conference> findByConferenceDescription(String description, Pageable pageable);

    Page<Conference> findByConferenceTopic(String topic, Pageable pageable);


}
