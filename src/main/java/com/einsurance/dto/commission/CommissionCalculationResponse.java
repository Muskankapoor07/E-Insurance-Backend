package com.einsurance.dto.commission;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommissionCalculationResponse {

    private Integer agentId;
    private String agentName;
    private String agentEmail;
    private int totalPoliciesSold;
    private BigDecimal totalPremiumVolume;
    private BigDecimal commissionPercentage;
    private BigDecimal totalCommissionPayable;
    private List<AgentPolicyCommissionDetail> policies;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AgentPolicyCommissionDetail {
        private Integer policyId;
        private Integer customerId;
        private String customerName;
        private String schemeName;
        private BigDecimal premium;
        private LocalDate dateIssued;
        private BigDecimal calculatedCommission;
        private boolean alreadyRecorded;
    }
}
