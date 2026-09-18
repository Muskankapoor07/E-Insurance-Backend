package com.einsurance.dto.policy;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiumCalculationResponse {

    private BigDecimal sumAssured;
    private Integer maturityPeriodYears;
    private Integer age;
    private BigDecimal rateOfInterest;
    private String paymentFrequency;
    private BigDecimal calculatedPremium;
    private BigDecimal totalInvestment;
    private BigDecimal maturityAmount;
    private String formulaExplanation;
}
