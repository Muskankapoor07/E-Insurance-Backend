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
public class PolicyPurchaseRequest {

    @NotNull(message = "Scheme ID is required")
    private Integer schemeId;

    private String policyDetails;

    @NotNull(message = "Premium amount is required")
    @DecimalMin(value = "0.01", message = "Premium must be greater than 0")
    private BigDecimal premium;

    @NotNull(message = "Maturity period is required (in years)")
    @Min(value = 1, message = "Maturity period must be at least 1 year")
    private Integer maturityPeriod;
}
