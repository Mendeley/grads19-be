package com.gradproject2019.userConference.repository;

import com.gradproject2019.userConference.persistance.UserConference;
import com.gradproject2019.users.persistance.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserConferenceRepository extends JpaRepository <UserConference, Long> {

    @Query(value = "SELECT * FROM user_conferences where user_id = :userId", nativeQuery = true)
    Optional<User> findByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM user_conferences where conference_id = :conferenceId", nativeQuery = true)
    Optional<User> findByConferenceId(@Param("conferenceId") Long conferenceId);
}
