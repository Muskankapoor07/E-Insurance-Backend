package com.einsurance.service;

import com.einsurance.dto.payment.PaymentReceiptResponse;
import com.einsurance.dto.payment.PaymentRequest;
import com.einsurance.dto.payment.PaymentResponse;
import com.einsurance.entity.Customer;
import com.einsurance.entity.Payment;
import com.einsurance.entity.Policy;
import com.einsurance.exception.ResourceNotFoundException;
import com.einsurance.mapper.PaymentMapper;
import com.einsurance.repository.CustomerRepository;
import com.einsurance.repository.PaymentRepository;
import com.einsurance.repository.PolicyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PolicyRepository policyRepository;
    private final CustomerRepository customerRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              PolicyRepository policyRepository,
                              CustomerRepository customerRepository,
                              PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.policyRepository = policyRepository;
        this.customerRepository = customerRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentResponse makePayment(PaymentRequest request, String customerUsername) {
        Policy policy = policyRepository.findById(request.getPolicyId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with ID: " + request.getPolicyId()));

        Customer customer;
        if (customerUsername != null && !customerUsername.isBlank()) {
            customer = customerRepository.findByUsername(customerUsername)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with username: " + customerUsername));
            // Ensure policy belongs to this customer
            if (!policy.getCustomer().getCustomerId().equals(customer.getCustomerId())) {
                throw new IllegalArgumentException("Policy does not belong to the authenticated customer");
            }
        } else if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + request.getCustomerId()));
        } else {
            customer = policy.getCustomer();
        }

        Payment payment = Payment.builder()
                .customer(customer)
                .policy(policy)
                .amount(request.getAmount())
                .paymentDate(request.getPaymentDate() != null ? request.getPaymentDate() : LocalDate.now())
                .build();

        Payment saved = paymentRepository.save(payment);
        return paymentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(paymentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPaymentsByCustomerId(Integer customerId, Pageable pageable) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with ID: " + customerId);
        }
        return paymentRepository.findByCustomer_CustomerId(customerId, pageable)
                .map(paymentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getMyPayments(String customerUsername, Pageable pageable) {
        return paymentRepository.findByCustomer_Username(customerUsername, pageable)
                .map(paymentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPaymentsByPolicyId(Integer policyId, Pageable pageable) {
        if (!policyRepository.existsById(policyId)) {
            throw new ResourceNotFoundException("Policy not found with ID: " + policyId);
        }
        return paymentRepository.findByPolicy_PolicyId(policyId, pageable)
                .map(paymentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentReceiptResponse getPaymentReceipt(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));

        BigDecimal totalPaidSoFar = paymentRepository.sumAmountByPolicyId(payment.getPolicy().getPolicyId());
        return paymentMapper.toReceiptResponse(payment, totalPaidSoFar);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalCustomerPayments(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with ID: " + customerId);
        }
        return paymentRepository.sumAmountByCustomerId(customerId);
    }
}
