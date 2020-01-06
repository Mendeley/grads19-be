package gradproject2019.conferences.repository;

import gradproject2019.conferences.persistence.Conference;
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
    @Query("UPDATE Conference SET " +
            "name = IfNull(:name, name), " +
            "dateTime = IfNull(:dateTime, dateTime), " +
            "city = IfNull(:city, city), " +
            "description = IfNull(:description, description), " +
            "topic = IfNull(:topic, topic) " +
            "WHERE id = :id")
    void updateConference(@Param("id") Long id,
                          @Param("name") String name,
                          @Param("dateTime") Instant dateTime,
                          @Param("city") String city,
                          @Param("description") String description,
                          @Param("topic") String topic);

}