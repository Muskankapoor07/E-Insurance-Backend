package com.einsurance.repository;

import com.einsurance.entity.Scheme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchemeRepository extends JpaRepository<Scheme, Integer> {
    List<Scheme> findByInsurancePlan_PlanId(Integer planId);
    Page<Scheme> findByInsurancePlan_PlanId(Integer planId, Pageable pageable);
    boolean existsBySchemeNameIgnoreCase(String schemeName);
}
