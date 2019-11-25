package com.gradproject2019.auth.repository;

import com.gradproject2019.auth.persistance.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<Token, UUID> {
//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM tokens where token =:token", nativeQuery = true)
//    void deleteByToken(@Param("token") String token);
}