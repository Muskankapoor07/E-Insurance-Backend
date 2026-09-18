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
public class PaymentReceiptResponse {

    private String receiptNumber;
    private Integer paymentId;
    private Integer policyId;
    private String schemeName;
    private String planName;
    private Integer customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private BigDecimal amountPaid;
    private LocalDate paymentDate;
    private BigDecimal policyPremium;
    private BigDecimal totalAmountPaidForPolicy;
    private String paymentStatus;
    private LocalDateTime generatedAt;
}
