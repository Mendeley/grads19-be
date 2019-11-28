package com.gradproject2019.users.repository;

import com.gradproject2019.users.persistance.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET " +
            "u.firstName = IfNull(:firstName, u.firstName), " +
            "u.lastName = IfNull(:lastName, u.lastName), " +
            "u.username = IfNull(:username, u.username), " +
            "u.email = IfNull(:email, u.email), " +
            "u.occupation = IfNull(:occupation, u.occupation) " +
            "WHERE u.id = :id")
    void updateUser(@Param("id") Long id,
                    @Param("firstName") String firstName,
                    @Param("lastName") String lastName,
                    @Param("username") String username,
                    @Param("email") String email,
                    @Param("occupation") String occupation);

    @Query(value = "SELECT * FROM users where username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM users where email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

}