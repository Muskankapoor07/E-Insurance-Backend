package com.einsurance.dto.payment;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Integer paymentId;
    private Integer customerId;
    private String customerName;
    private Integer policyId;
    private String schemeName;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private LocalDateTime createdAt;
}
