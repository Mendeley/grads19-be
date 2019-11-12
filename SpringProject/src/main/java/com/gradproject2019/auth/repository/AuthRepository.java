package com.gradproject2019.auth.repository;

import com.gradproject2019.auth.persistance.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Token, Long> {
}