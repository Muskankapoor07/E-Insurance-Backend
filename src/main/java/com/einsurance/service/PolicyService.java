package com.einsurance.service;

import com.einsurance.dto.policy.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PolicyService {
    PolicyResponse purchasePolicy(String customerUsername, PolicyPurchaseRequest request);
    PolicyResponse createPolicy(PolicyRequest request);
    PolicyResponse getPolicyById(Integer policyId);
    Page<PolicyResponse> getAllPolicies(Pageable pageable);
    Page<PolicyResponse> getPoliciesByCustomerId(Integer customerId, Pageable pageable);
    Page<PolicyResponse> getMyPolicies(String customerUsername, Pageable pageable);
    Page<PolicyResponse> getPoliciesByAgentId(Integer agentId, Pageable pageable);
    PremiumCalculationResponse calculatePremium(PremiumCalculationRequest request);
    PolicyResponse updatePolicy(Integer policyId, PolicyRequest request);
    void deletePolicy(Integer policyId);
}
