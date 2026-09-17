package com.einsurance.service;

import com.einsurance.dto.agent.InsuranceAgentRequest;
import com.einsurance.dto.agent.InsuranceAgentResponse;
import com.einsurance.entity.InsuranceAgent;
import com.einsurance.mapper.InsuranceAgentMapper;
import com.einsurance.repository.InsuranceAgentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InsuranceAgentServiceImpl implements InsuranceAgentService {

    private final InsuranceAgentRepository agentRepository;
    private final InsuranceAgentMapper agentMapper;
    private final PasswordEncoder passwordEncoder;

    public InsuranceAgentServiceImpl(
            InsuranceAgentRepository agentRepository,
            InsuranceAgentMapper agentMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.agentRepository = agentRepository;
        this.agentMapper = agentMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public InsuranceAgentResponse registerAgent(
            InsuranceAgentRequest request
    ) {

        if (agentRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (agentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        InsuranceAgent agent = agentMapper.toEntity(request);

        agent.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        InsuranceAgent savedAgent = agentRepository.save(agent);

        return agentMapper.toResponse(savedAgent);
    }
}