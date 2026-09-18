package com.einsurance.dto.commission;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommissionRequest {

    @NotNull(message = "Agent ID is required")
    private Integer agentId;

    @NotNull(message = "Policy ID is required")
    private Integer policyId;

    @NotNull(message = "Commission amount is required")
    @DecimalMin(value = "0.01", message = "Commission amount must be greater than 0")
    private BigDecimal commissionAmount;
}
