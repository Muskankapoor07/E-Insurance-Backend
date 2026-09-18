package com.einsurance.mapper;

import com.einsurance.dto.payment.PaymentReceiptResponse;
import com.einsurance.dto.payment.PaymentRequest;
import com.einsurance.dto.payment.PaymentResponse;
import com.einsurance.entity.Customer;
import com.einsurance.entity.Payment;
import com.einsurance.entity.Policy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentRequest request, Customer customer, Policy policy) {
        LocalDate date = request.getPaymentDate() != null ? request.getPaymentDate() : LocalDate.now();
        return Payment.builder()
                .customer(customer)
                .policy(policy)
                .amount(request.getAmount())
                .paymentDate(date)
                .build();
    }

    public PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .customerId(payment.getCustomer() != null ? payment.getCustomer().getCustomerId() : null)
                .customerName(payment.getCustomer() != null ? payment.getCustomer().getFullName() : null)
                .policyId(payment.getPolicy() != null ? payment.getPolicy().getPolicyId() : null)
                .schemeName(payment.getPolicy() != null && payment.getPolicy().getScheme() != null
                        ? payment.getPolicy().getScheme().getSchemeName()
                        : null)
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    public PaymentReceiptResponse toReceiptResponse(Payment payment, BigDecimal totalPaidSoFar) {
        Policy policy = payment.getPolicy();
        Customer customer = payment.getCustomer();
        String schemeName = (policy != null && policy.getScheme() != null) ? policy.getScheme().getSchemeName() : "";
        String planName = (policy != null && policy.getScheme() != null && policy.getScheme().getInsurancePlan() != null)
                ? policy.getScheme().getInsurancePlan().getPlanName()
                : "";

        return PaymentReceiptResponse.builder()
                .receiptNumber("RCPT-" + payment.getPaymentId() + "-" + System.currentTimeMillis() % 100000)
                .paymentId(payment.getPaymentId())
                .policyId(policy != null ? policy.getPolicyId() : null)
                .schemeName(schemeName)
                .planName(planName)
                .customerId(customer != null ? customer.getCustomerId() : null)
                .customerName(customer != null ? customer.getFullName() : null)
                .customerEmail(customer != null ? customer.getEmail() : null)
                .customerPhone(customer != null ? customer.getPhone() : null)
                .amountPaid(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .policyPremium(policy != null ? policy.getPremium() : BigDecimal.ZERO)
                .totalAmountPaidForPolicy(totalPaidSoFar)
                .paymentStatus("SUCCESS")
                .generatedAt(LocalDateTime.now())
                .build();
    }
}
