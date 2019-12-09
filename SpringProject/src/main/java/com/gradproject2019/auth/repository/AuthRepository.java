package com.gradproject2019.auth.repository;

import com.gradproject2019.auth.persistence.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<Token, UUID> {
}