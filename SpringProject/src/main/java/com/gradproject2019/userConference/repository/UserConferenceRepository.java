package com.gradproject2019.userConference.repository;

import com.gradproject2019.userConference.persistance.UserConference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConferenceRepository extends JpaRepository<UserConference, Long> {

}