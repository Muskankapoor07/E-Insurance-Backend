package com.einsurance.controller;

import com.einsurance.dto.agent.InsuranceAgentRequest;
import com.einsurance.dto.agent.InsuranceAgentResponse;
import com.einsurance.service.InsuranceAgentService;
import jakarta.validation.Valid;
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

        InsuranceAgentResponse response =
                agentService.registerAgent(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}