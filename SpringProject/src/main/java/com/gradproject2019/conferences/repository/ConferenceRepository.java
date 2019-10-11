package com.gradproject2019.conferences.repository;

import com.gradproject2019.conferences.persistance.Conference;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends CrudRepository<Conference, Long> {
}