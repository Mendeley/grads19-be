package gradproject2019.userConference.repository;

import gradproject2019.userConference.persistence.UserConference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserConferenceRepository extends JpaRepository<UserConference, Long> {

    @Query(value =
            "SELECT * FROM user_conferences WHERE user_id = :userId",
            nativeQuery = true)
    List<UserConference> findByUserId(@Param("userId") Long userId);
}