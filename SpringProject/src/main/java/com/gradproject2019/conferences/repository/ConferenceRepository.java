package com.gradproject2019.conferences.repository;

import com.gradproject2019.conferences.persistance.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    //@Modifying  //DOES NOT WORK
    //@Query("UPDATE conferences SET name = COALESCE(:name, name), date_time = COALESCE(:dateTime, date_time), city = COALESCE(:city, city), description = COALESCE(:description, description), topic = COALESCE(:topic, topic) WHERE id = :id;")
    //Conference updateConference(@Param("id") Long id, @Param("name") String name, @Param("dateTime") Instant dateTime, @Param("city") String city, @Param("description") String description, @Param("topic") String topic);
}