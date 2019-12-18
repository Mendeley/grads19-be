package com.gradproject2019.userConference.repository;

import com.gradproject2019.userConference.persistence.UserConference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserConferenceRepository extends JpaRepository<UserConference, Long> {

    @Query(value = "SELECT EXISTS(" +
            "SELECT 1 " +
            "FROM user_conferences " +
            "WHERE user_id = :userId " +
            "AND conference_id = :conferenceId)",
            nativeQuery = true)
    int exists(@Param("userId") Long userId, @Param("conferenceId") Long conferenceId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE " +
            "FROM user_conferences " +
            "WHERE (user_id, conference_id) = (:userId, :conferenceId)",
            nativeQuery = true)
    void deleteById(@Param("userId") Long userId, @Param("conferenceId") Long conferenceId);

    @Query(value = "SELECT EXISTS(" +
            "SELECT * " +
            "FROM user_conferences " +
            "WHERE conference_id = :conferenceId)",
            nativeQuery = true)
    int existsByConferenceId(@Param("conferenceId") Long conferenceId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE " +
            "FROM user_conferences " +
            "WHERE conference_id = :conferenceId",
            nativeQuery = true)
    void deleteByConferenceId(@Param("conferenceId") Long conferenceId);
}