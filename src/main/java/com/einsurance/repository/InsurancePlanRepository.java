package com.einsurance.repository;

import com.einsurance.entity.InsurancePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsurancePlanRepository extends JpaRepository<InsurancePlan, Integer> {
    Optional<InsurancePlan> findByPlanNameIgnoreCase(String planName);
    boolean existsByPlanNameIgnoreCase(String planName);
}
