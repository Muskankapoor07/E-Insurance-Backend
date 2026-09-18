package com.einsurance.dto.policy;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiumCalculationRequest {

    @NotNull(message = "Sum assured / investment amount is required")
    @DecimalMin(value = "1000.00", message = "Sum assured must be at least 1000")
    private BigDecimal sumAssured;

    @NotNull(message = "Maturity period (years) is required")
    @Min(value = 1, message = "Maturity period must be at least 1 year")
    private Integer maturityPeriodYears;

    @NotNull(message = "Customer age is required")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    // Optional interest rate percentage, if null default base rate (e.g. 7.5%) is used
    private BigDecimal rateOfInterest;

    // Optional frequency: MONTHLY, QUARTERLY, HALF_YEARLY, ANNUALLY
    private String paymentFrequency;
}
