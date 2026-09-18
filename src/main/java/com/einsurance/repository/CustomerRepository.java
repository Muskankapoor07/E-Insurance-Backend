package com.einsurance.repository;

import com.einsurance.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByEmail(String email);

    java.util.List<Customer> findByInsuranceAgent_AgentId(Integer agentId);

    org.springframework.data.domain.Page<Customer> findByInsuranceAgent_AgentId(Integer agentId, org.springframework.data.domain.Pageable pageable);
}