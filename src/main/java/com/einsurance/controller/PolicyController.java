package com.einsurance.controller;

import com.einsurance.dto.policy.*;
import com.einsurance.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    // Use Case 4: Customer purchases a policy
    @PostMapping("/purchase")
    public ResponseEntity<PolicyResponse> purchasePolicy(
            Authentication authentication,
            @Valid @RequestBody PolicyPurchaseRequest request) {
        String username = authentication != null ? authentication.getName() : null;
        PolicyResponse response = policyService.purchasePolicy(username, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Direct policy creation (Admin)
    @PostMapping
    public ResponseEntity<PolicyResponse> createPolicy(
            @Valid @RequestBody PolicyRequest request) {
        PolicyResponse response = policyService.createPolicy(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // View specific policy
    @GetMapping("/{policyId}")
    public ResponseEntity<PolicyResponse> getPolicyById(@PathVariable Integer policyId) {
        PolicyResponse response = policyService.getPolicyById(policyId);
        return ResponseEntity.ok(response);
    }

    // View all policies (Admin)
    @GetMapping
    public ResponseEntity<Page<PolicyResponse>> getAllPolicies(Pageable pageable) {
        Page<PolicyResponse> response = policyService.getAllPolicies(pageable);
        return ResponseEntity.ok(response);
    }

    // Use Case 2 (Admin): Admin navigates to 'Customer Policies' section and views customer-specific policies
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<PolicyResponse>> getPoliciesByCustomerId(
            @PathVariable Integer customerId,
            Pageable pageable) {
        Page<PolicyResponse> response = policyService.getPoliciesByCustomerId(customerId, pageable);
        return ResponseEntity.ok(response);
    }

    // Use Case 2 (Customer): Customer navigates to 'My Policies' section
    @GetMapping("/my-policies")
    public ResponseEntity<Page<PolicyResponse>> getMyPolicies(
            Authentication authentication,
            Pageable pageable) {
        String username = authentication != null ? authentication.getName() : null;
        Page<PolicyResponse> response = policyService.getMyPolicies(username, pageable);
        return ResponseEntity.ok(response);
    }

    // Agent's policies
    @GetMapping("/agent/{agentId}")
    public ResponseEntity<Page<PolicyResponse>> getPoliciesByAgentId(
            @PathVariable Integer agentId,
            Pageable pageable) {
        Page<PolicyResponse> response = policyService.getPoliciesByAgentId(agentId, pageable);
        return ResponseEntity.ok(response);
    }

    // Use Case 5: Premium Calculation (Customer, Insurance Agent)
    @PostMapping("/calculate-premium")
    public ResponseEntity<PremiumCalculationResponse> calculatePremium(
            @Valid @RequestBody PremiumCalculationRequest request) {
        PremiumCalculationResponse response = policyService.calculatePremium(request);
        return ResponseEntity.ok(response);
    }

    // Update policy (Admin)
    @PutMapping("/{policyId}")
    public ResponseEntity<PolicyResponse> updatePolicy(
            @PathVariable Integer policyId,
            @Valid @RequestBody PolicyRequest request) {
        PolicyResponse response = policyService.updatePolicy(policyId, request);
        return ResponseEntity.ok(response);
    }

    // Delete policy (Admin)
    @DeleteMapping("/{policyId}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Integer policyId) {
        policyService.deletePolicy(policyId);
        return ResponseEntity.noContent().build();
    }
}
