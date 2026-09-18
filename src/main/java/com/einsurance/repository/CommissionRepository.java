package com.einsurance.repository;

import com.einsurance.entity.Commission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Integer> {
    List<Commission> findByInsuranceAgent_AgentId(Integer agentId);
    Page<Commission> findByInsuranceAgent_AgentId(Integer agentId, Pageable pageable);
    List<Commission> findByPolicy_PolicyId(Integer policyId);
    boolean existsByPolicy_PolicyId(Integer policyId);

    @Query("SELECT COALESCE(SUM(c.commissionAmount), 0) FROM Commission c WHERE c.insuranceAgent.agentId = :agentId")
    BigDecimal sumCommissionByAgentId(@Param("agentId") Integer agentId);
}
