package com.einsurance.mapper;

import com.einsurance.dto.policy.PolicyRequest;
import com.einsurance.dto.policy.PolicyResponse;
import com.einsurance.entity.Customer;
import com.einsurance.entity.Policy;
import com.einsurance.entity.Scheme;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PolicyMapper {

    public Policy toEntity(PolicyRequest request, Customer customer, Scheme scheme) {
        return Policy.builder()
                .customer(customer)
                .scheme(scheme)
                .policyDetails(request.getPolicyDetails())
                .premium(request.getPremium())
                .dateIssued(request.getDateIssued())
                .maturityPeriod(request.getMaturityPeriod())
                .policyLapseDate(request.getPolicyLapseDate())
                .build();
    }

    public PolicyResponse toResponse(Policy policy, BigDecimal totalPaid) {
        return PolicyResponse.builder()
                .policyId(policy.getPolicyId())
                .customerId(policy.getCustomer() != null ? policy.getCustomer().getCustomerId() : null)
                .customerName(policy.getCustomer() != null ? policy.getCustomer().getFullName() : null)
                .customerEmail(policy.getCustomer() != null ? policy.getCustomer().getEmail() : null)
                .schemeId(policy.getScheme() != null ? policy.getScheme().getSchemeId() : null)
                .schemeName(policy.getScheme() != null ? policy.getScheme().getSchemeName() : null)
                .planName(policy.getScheme() != null && policy.getScheme().getInsurancePlan() != null
                        ? policy.getScheme().getInsurancePlan().getPlanName()
                        : null)
                .policyDetails(policy.getPolicyDetails())
                .premium(policy.getPremium())
                .dateIssued(policy.getDateIssued())
                .maturityPeriod(policy.getMaturityPeriod())
                .policyLapseDate(policy.getPolicyLapseDate())
                .totalPaid(totalPaid != null ? totalPaid : BigDecimal.ZERO)
                .createdAt(policy.getCreatedAt())
                .build();
    }

    public PolicyResponse toResponse(Policy policy) {
        return toResponse(policy, BigDecimal.ZERO);
    }
}
