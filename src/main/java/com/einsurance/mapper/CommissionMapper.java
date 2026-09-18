package com.einsurance.mapper;

import com.einsurance.dto.commission.CommissionRequest;
import com.einsurance.dto.commission.CommissionResponse;
import com.einsurance.entity.Commission;
import com.einsurance.entity.InsuranceAgent;
import com.einsurance.entity.Policy;
import org.springframework.stereotype.Component;

@Component
public class CommissionMapper {

    public Commission toEntity(CommissionRequest request, InsuranceAgent agent, Policy policy) {
        return Commission.builder()
                .insuranceAgent(agent)
                .policy(policy)
                .commissionAmount(request.getCommissionAmount())
                .build();
    }

    public CommissionResponse toResponse(Commission commission) {
        InsuranceAgent agent = commission.getInsuranceAgent();
        Policy policy = commission.getPolicy();
        return CommissionResponse.builder()
                .commissionId(commission.getCommissionId())
                .agentId(agent != null ? agent.getAgentId() : null)
                .agentName(agent != null ? agent.getFullName() : null)
                .policyId(policy != null ? policy.getPolicyId() : null)
                .customerName(policy != null && policy.getCustomer() != null ? policy.getCustomer().getFullName() : null)
                .schemeName(policy != null && policy.getScheme() != null ? policy.getScheme().getSchemeName() : null)
                .policyPremium(policy != null ? policy.getPremium() : null)
                .commissionAmount(commission.getCommissionAmount())
                .createdAt(commission.getCreatedAt())
                .build();
    }
}
