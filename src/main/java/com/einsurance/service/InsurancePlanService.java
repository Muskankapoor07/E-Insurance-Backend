package com.einsurance.service;

import com.einsurance.dto.plan.InsurancePlanRequest;
import com.einsurance.dto.plan.InsurancePlanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InsurancePlanService {
    InsurancePlanResponse createPlan(InsurancePlanRequest request);
    InsurancePlanResponse getPlanById(Integer planId);
    Page<InsurancePlanResponse> getAllPlans(Pageable pageable);
    List<InsurancePlanResponse> getAllPlansList();
    InsurancePlanResponse updatePlan(Integer planId, InsurancePlanRequest request);
    void deletePlan(Integer planId);
}
