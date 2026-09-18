package com.einsurance.service;

import com.einsurance.dto.payment.PaymentReceiptResponse;
import com.einsurance.dto.payment.PaymentRequest;
import com.einsurance.dto.payment.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentResponse makePayment(PaymentRequest request, String customerUsername);
    PaymentResponse getPaymentById(Integer paymentId);
    Page<PaymentResponse> getAllPayments(Pageable pageable);
    Page<PaymentResponse> getPaymentsByCustomerId(Integer customerId, Pageable pageable);
    Page<PaymentResponse> getMyPayments(String customerUsername, Pageable pageable);
    Page<PaymentResponse> getPaymentsByPolicyId(Integer policyId, Pageable pageable);
    PaymentReceiptResponse getPaymentReceipt(Integer paymentId);
    BigDecimal getTotalCustomerPayments(Integer customerId);
}
