package com.gradproject2019.users.repository;

import com.gradproject2019.users.persistance.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query (value = "SELECT * FROM users where username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);

    @Query (value = "SELECT * FROM users where email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

}    //method in service impl: if exists then throw error