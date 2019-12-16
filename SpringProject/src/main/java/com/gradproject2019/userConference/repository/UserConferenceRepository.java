package com.gradproject2019.userConference.repository;

import com.gradproject2019.userConference.persistence.UserConference;
import com.gradproject2019.users.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserConferenceRepository extends JpaRepository<UserConference, Long> {

    @Query(value =
            "SELECT * " +
                    "FROM user_conference " +
                    "WHERE userId = :user_id",
            nativeQuery = true)
    List<UserConference> findByUserId(@Param("user_id") Long userId);

}