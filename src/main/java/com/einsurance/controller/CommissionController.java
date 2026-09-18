package com.einsurance.controller;

import com.einsurance.dto.commission.CommissionCalculationResponse;
import com.einsurance.dto.commission.CommissionRequest;
import com.einsurance.dto.commission.CommissionResponse;
import com.einsurance.entity.InsuranceAgent;
import com.einsurance.exception.ResourceNotFoundException;
import com.einsurance.repository.InsuranceAgentRepository;
import com.einsurance.service.CommissionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commissions")
public class CommissionController {

    private final CommissionService commissionService;
    private final InsuranceAgentRepository agentRepository;

    public CommissionController(CommissionService commissionService,
                                InsuranceAgentRepository agentRepository) {
        this.commissionService = commissionService;
        this.agentRepository = agentRepository;
    }

    // Use Case 6: Commission Calculation (Admin)
    // Admin navigates to 'Commission Calculator' section, selects an insurance agent, system calculates commission
    @GetMapping("/calculate")
    public ResponseEntity<CommissionCalculationResponse> calculateCommission(
            @RequestParam Integer agentId,
            @RequestParam(required = false, defaultValue = "10.0") Double commissionPercentage) {
        CommissionCalculationResponse response = commissionService.calculateCommissionForAgent(agentId, commissionPercentage);
        return ResponseEntity.ok(response);
    }

    // Record commission
    @PostMapping
    public ResponseEntity<CommissionResponse> recordCommission(
            @Valid @RequestBody CommissionRequest request) {
        CommissionResponse response = commissionService.recordCommission(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get commissions for a specific agent (Admin)
    @GetMapping("/agent/{agentId}")
    public ResponseEntity<Page<CommissionResponse>> getCommissionsByAgentId(
            @PathVariable Integer agentId,
            Pageable pageable) {
        Page<CommissionResponse> response = commissionService.getCommissionsByAgentId(agentId, pageable);
        return ResponseEntity.ok(response);
    }

    // Agent views own commissions
    @GetMapping("/my-commissions")
    public ResponseEntity<Page<CommissionResponse>> getMyCommissions(
            Authentication authentication,
            Pageable pageable) {
        String username = authentication != null ? authentication.getName() : null;
        InsuranceAgent agent = agentRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with username: " + username));

        Page<CommissionResponse> response = commissionService.getCommissionsByAgentId(agent.getAgentId(), pageable);
        return ResponseEntity.ok(response);
    }

    // Get all commissions (Admin)
    @GetMapping
    public ResponseEntity<Page<CommissionResponse>> getAllCommissions(Pageable pageable) {
        Page<CommissionResponse> response = commissionService.getAllCommissions(pageable);
        return ResponseEntity.ok(response);
    }
}
