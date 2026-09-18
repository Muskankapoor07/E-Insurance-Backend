package com.einsurance.service;

import com.einsurance.dto.agent.InsuranceAgentRequest;
import com.einsurance.dto.agent.InsuranceAgentResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InsuranceAgentService {

    InsuranceAgentResponse registerAgent(InsuranceAgentRequest request);
    InsuranceAgentResponse getAgentById(Integer agentId);
    Page<InsuranceAgentResponse> getAllAgents(Pageable pageable);
    InsuranceAgentResponse updateAgent(Integer agentId, InsuranceAgentRequest request);
    void deleteAgent(Integer agentId);
}