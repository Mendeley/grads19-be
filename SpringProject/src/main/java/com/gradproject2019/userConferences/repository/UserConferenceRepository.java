package com.gradproject2019.userConferences.repository;

import com.gradproject2019.userConferences.persistance.UserConference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConferenceRepository extends JpaRepository <UserConference, Long> {
}
