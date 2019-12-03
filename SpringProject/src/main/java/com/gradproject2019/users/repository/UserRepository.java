package com.gradproject2019.users.repository;

import com.gradproject2019.users.persistance.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User SET " +
            "firstName = IfNull(:firstName, firstName), " +
            "lastName = IfNull(:lastName, lastName), " +
            "username = IfNull(:username, username), " +
            "email = IfNull(:email, email), " +
            "occupation = IfNull(:occupation, occupation) " +
            "WHERE id = :id")
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

    @Query(value = "SELECT * FROM users WHERE first_name like CONCAT('%', :searchInput,'%') OR last_name like CONCAT('%', :searchInput, '%') ORDER BY last_name ASC", nativeQuery = true)
    List<User> searchByName(@Param("searchInput") String searchInput);
}