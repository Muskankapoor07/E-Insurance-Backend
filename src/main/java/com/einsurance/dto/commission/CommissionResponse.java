package com.einsurance.dto.commission;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommissionResponse {

    private Integer commissionId;
    private Integer agentId;
    private String agentName;
    private Integer policyId;
    private String customerName;
    private String schemeName;
    private BigDecimal policyPremium;
    private BigDecimal commissionAmount;
    private LocalDateTime createdAt;
}
