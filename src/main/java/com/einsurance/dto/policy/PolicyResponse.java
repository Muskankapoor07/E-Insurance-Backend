package com.einsurance.dto.policy;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyResponse {

    private Integer policyId;
    private Integer customerId;
    private String customerName;
    private String customerEmail;
    private Integer schemeId;
    private String schemeName;
    private String planName;
    private String policyDetails;
    private BigDecimal premium;
    private LocalDate dateIssued;
    private Integer maturityPeriod;
    private LocalDate policyLapseDate;
    private BigDecimal totalPaid;
    private LocalDateTime createdAt;
}
