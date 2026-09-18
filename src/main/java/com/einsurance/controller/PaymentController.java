package com.einsurance.controller;

import com.einsurance.dto.payment.PaymentReceiptResponse;
import com.einsurance.dto.payment.PaymentRequest;
import com.einsurance.dto.payment.PaymentResponse;
import com.einsurance.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(
            Authentication authentication,
            @Valid @RequestBody PaymentRequest request) {
        String username = authentication != null ? authentication.getName() : null;
        PaymentResponse response = paymentService.makePayment(request, username);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Integer paymentId) {
        PaymentResponse response = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PaymentResponse>> getAllPayments(Pageable pageable) {
        Page<PaymentResponse> response = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(response);
    }

    // Use Case 2 (Admin): Admin views customer-specific payments
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<PaymentResponse>> getPaymentsByCustomerId(
            @PathVariable Integer customerId,
            Pageable pageable) {
        Page<PaymentResponse> response = paymentService.getPaymentsByCustomerId(customerId, pageable);
        return ResponseEntity.ok(response);
    }

    // Use Case 2 (Customer): Customer views own payments
    @GetMapping("/my-payments")
    public ResponseEntity<Page<PaymentResponse>> getMyPayments(
            Authentication authentication,
            Pageable pageable) {
        String username = authentication != null ? authentication.getName() : null;
        Page<PaymentResponse> response = paymentService.getMyPayments(username, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/policy/{policyId}")
    public ResponseEntity<Page<PaymentResponse>> getPaymentsByPolicyId(
            @PathVariable Integer policyId,
            Pageable pageable) {
        Page<PaymentResponse> response = paymentService.getPaymentsByPolicyId(policyId, pageable);
        return ResponseEntity.ok(response);
    }

    // Receipt / Invoice generation
    @GetMapping("/receipt/{paymentId}")
    public ResponseEntity<PaymentReceiptResponse> getPaymentReceipt(@PathVariable Integer paymentId) {
        PaymentReceiptResponse response = paymentService.getPaymentReceipt(paymentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total/customer/{customerId}")
    public ResponseEntity<BigDecimal> getTotalCustomerPayments(@PathVariable Integer customerId) {
        BigDecimal total = paymentService.getTotalCustomerPayments(customerId);
        return ResponseEntity.ok(total);
    }
}
