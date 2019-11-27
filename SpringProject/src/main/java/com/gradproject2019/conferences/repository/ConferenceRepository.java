package com.gradproject2019.conferences.repository;

import com.gradproject2019.conferences.persistance.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Conference c SET " +
            "c.name = IfNull(:name, c.name), " +
            "c.dateTime = IfNull(:dateTime, c.dateTime), " +
            "c.city = IfNull(:city, c.city), " +
            "c.description = IfNull(:description, c.description), " +
            "c.topic = IfNull(:topic, c.topic) " +
            "WHERE c.id = :id")
    void updateConference(@Param("id") Long id, @Param("name") String name, @Param("dateTime") Instant dateTime, @Param("city") String city, @Param("description") String description, @Param("topic") String topic);

}