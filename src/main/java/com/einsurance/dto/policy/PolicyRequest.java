package com.einsurance.dto.policy;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyRequest {

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotNull(message = "Scheme ID is required")
    private Integer schemeId;

    @NotBlank(message = "Policy details are required")
    private String policyDetails;

    @NotNull(message = "Premium amount is required")
    @DecimalMin(value = "0.01", message = "Premium must be greater than 0")
    private BigDecimal premium;

    @NotNull(message = "Date issued is required")
    private LocalDate dateIssued;

    @NotNull(message = "Maturity period is required")
    @Min(value = 1, message = "Maturity period must be at least 1 year/month")
    private Integer maturityPeriod;

    @NotNull(message = "Policy lapse date is required")
    private LocalDate policyLapseDate;
}
