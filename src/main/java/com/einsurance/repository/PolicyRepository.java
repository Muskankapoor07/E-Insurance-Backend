package com.einsurance.repository;

import com.einsurance.entity.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Integer> {
    List<Policy> findByCustomer_CustomerId(Integer customerId);
    Page<Policy> findByCustomer_CustomerId(Integer customerId, Pageable pageable);
    List<Policy> findByCustomer_Username(String username);
    Page<Policy> findByCustomer_Username(String username, Pageable pageable);
    List<Policy> findByScheme_SchemeId(Integer schemeId);
    List<Policy> findByCustomer_InsuranceAgent_AgentId(Integer agentId);
    Page<Policy> findByCustomer_InsuranceAgent_AgentId(Integer agentId, Pageable pageable);
}
