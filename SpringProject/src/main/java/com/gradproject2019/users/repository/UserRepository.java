package com.gradproject2019.users.repository;

import com.gradproject2019.users.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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
            "occupation = IfNull(:occupation, occupation), " +
            "managerId = IfNull(:managerId, managerId) " +
            "WHERE id = :id")
    void updateUser(@Param("id") Long id,
                    @Param("firstName") String firstName,
                    @Param("lastName") String lastName,
                    @Param("username") String username,
                    @Param("email") String email,
                    @Param("occupation") String occupation,
                    @Param("managerId") Long managerId);

    @Query(value =
            "SELECT * " +
            "FROM users " +
            "WHERE username = :username",
            nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);

    @Query(value =
            "SELECT * " +
            "FROM users " +
            "WHERE email = :email",
            nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value =
            "SELECT * " +
            "FROM users " +
            "WHERE CONCAT(first_name, ' ', last_name) " +
            "LIKE CONCAT('%', :query, '%') " +
            "ORDER BY last_name ASC",
            nativeQuery = true)
    List<User> searchByName(@Param("query") String query);

    @Query(value = "SELECT EXISTS(" +
            "SELECT 1 " +
            "FROM users " +
            "WHERE id = :requestedUserId " +
            "AND " +
            "(" +
            "(manager_id IS NOT NULL " +
            "AND manager_id = :requestingUserId) " +
            "OR " +
            "(:requestingUserManagerId IS NOT NULL " +
            "AND id = :requestingUserManagerId)" +
            "))",
            nativeQuery = true)
    int hasManagerEmployeeRelationship(@Param("requestedUserId") Long requestedUserId,
                                       @Param("requestingUserId") Long requestingUserId,
                                       @Param("requestingUserManagerId") Long requestingUserManagerId);
}