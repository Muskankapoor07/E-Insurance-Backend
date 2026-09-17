package com.einsurance.repository;

import com.einsurance.entity.InsuranceAgent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InsuranceAgentRepository
        extends JpaRepository<InsuranceAgent, Integer> {

    Optional<InsuranceAgent> findByUsername(String username);

    Optional<InsuranceAgent> findByEmail(String email);
}