package com.einsurance.mapper;

import com.einsurance.dto.agent.InsuranceAgentRequest;
import com.einsurance.dto.agent.InsuranceAgentResponse;
import com.einsurance.entity.InsuranceAgent;
import org.springframework.stereotype.Component;

@Component
public class InsuranceAgentMapper {

    public InsuranceAgent toEntity(InsuranceAgentRequest request) {

        return InsuranceAgent.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .build();
    }

    public InsuranceAgentResponse toResponse(InsuranceAgent agent) {

        return InsuranceAgentResponse.builder()
                .agentId(agent.getAgentId())
                .username(agent.getUsername())
                .email(agent.getEmail())
                .fullName(agent.getFullName())
                .createdAt(agent.getCreatedAt())
                .build();
    }

    public void updateEntity(InsuranceAgent agent, InsuranceAgentRequest request) {
        agent.setFullName(request.getFullName());
        agent.setEmail(request.getEmail());
    }
}