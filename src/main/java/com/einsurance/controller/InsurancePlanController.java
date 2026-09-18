package com.einsurance.controller;

import com.einsurance.dto.plan.InsurancePlanRequest;
import com.einsurance.dto.plan.InsurancePlanResponse;
import com.einsurance.service.InsurancePlanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class InsurancePlanController {

    private final InsurancePlanService planService;

    public InsurancePlanController(InsurancePlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<InsurancePlanResponse> createPlan(
            @Valid @RequestBody InsurancePlanRequest request) {
        InsurancePlanResponse response = planService.createPlan(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<InsurancePlanResponse> getPlanById(@PathVariable Integer planId) {
        InsurancePlanResponse response = planService.getPlanById(planId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<InsurancePlanResponse>> getAllPlans(Pageable pageable) {
        Page<InsurancePlanResponse> response = planService.getAllPlans(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InsurancePlanResponse>> getAllPlansList() {
        List<InsurancePlanResponse> response = planService.getAllPlansList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{planId}")
    public ResponseEntity<InsurancePlanResponse> updatePlan(
            @PathVariable Integer planId,
            @Valid @RequestBody InsurancePlanRequest request) {
        InsurancePlanResponse response = planService.updatePlan(planId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Integer planId) {
        planService.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }
}
