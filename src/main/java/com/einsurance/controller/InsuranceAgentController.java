package com.einsurance.controller;

import com.einsurance.dto.agent.InsuranceAgentRequest;
import com.einsurance.dto.agent.InsuranceAgentResponse;
import com.einsurance.service.InsuranceAgentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agents")
public class InsuranceAgentController {

    private final InsuranceAgentService agentService;

    public InsuranceAgentController(InsuranceAgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/register")
    public ResponseEntity<InsuranceAgentResponse> registerAgent(
            @Valid @RequestBody InsuranceAgentRequest request
    ) {
        InsuranceAgentResponse response = agentService.registerAgent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<InsuranceAgentResponse> createAgent(
            @Valid @RequestBody InsuranceAgentRequest request
    ) {
        InsuranceAgentResponse response = agentService.registerAgent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{agentId}")
    public ResponseEntity<InsuranceAgentResponse> getAgentById(@PathVariable Integer agentId) {
        InsuranceAgentResponse response = agentService.getAgentById(agentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<InsuranceAgentResponse>> getAllAgents(Pageable pageable) {
        Page<InsuranceAgentResponse> response = agentService.getAllAgents(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{agentId}")
    public ResponseEntity<InsuranceAgentResponse> updateAgent(
            @PathVariable Integer agentId,
            @Valid @RequestBody InsuranceAgentRequest request) {
        InsuranceAgentResponse response = agentService.updateAgent(agentId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{agentId}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Integer agentId) {
        agentService.deleteAgent(agentId);
        return ResponseEntity.noContent().build();
    }
}