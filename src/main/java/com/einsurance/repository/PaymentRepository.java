package com.einsurance.repository;

import com.einsurance.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByCustomer_CustomerId(Integer customerId);
    Page<Payment> findByCustomer_CustomerId(Integer customerId, Pageable pageable);
    List<Payment> findByCustomer_Username(String username);
    Page<Payment> findByCustomer_Username(String username, Pageable pageable);
    List<Payment> findByPolicy_PolicyId(Integer policyId);
    Page<Payment> findByPolicy_PolicyId(Integer policyId, Pageable pageable);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.customer.customerId = :customerId")
    BigDecimal sumAmountByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.policy.policyId = :policyId")
    BigDecimal sumAmountByPolicyId(@Param("policyId") Integer policyId);
}
