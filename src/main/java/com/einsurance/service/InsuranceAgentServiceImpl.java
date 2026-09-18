package com.einsurance.service;

import com.einsurance.dto.agent.InsuranceAgentRequest;
import com.einsurance.dto.agent.InsuranceAgentResponse;
import com.einsurance.entity.InsuranceAgent;
import com.einsurance.exception.DuplicateResourceException;
import com.einsurance.exception.ResourceNotFoundException;
import com.einsurance.mapper.InsuranceAgentMapper;
import com.einsurance.repository.InsuranceAgentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            throw new DuplicateResourceException("Username already exists: " + request.getUsername());
        }

        if (agentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + request.getEmail());
        }

        InsuranceAgent agent = agentMapper.toEntity(request);

        agent.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        InsuranceAgent savedAgent = agentRepository.save(agent);

        return agentMapper.toResponse(savedAgent);
    }

    @Override
    @Transactional(readOnly = true)
    public InsuranceAgentResponse getAgentById(Integer agentId) {
        InsuranceAgent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance Agent not found with ID: " + agentId));
        return agentMapper.toResponse(agent);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InsuranceAgentResponse> getAllAgents(Pageable pageable) {
        return agentRepository.findAll(pageable)
                .map(agentMapper::toResponse);
    }

    @Override
    public InsuranceAgentResponse updateAgent(Integer agentId, InsuranceAgentRequest request) {
        InsuranceAgent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance Agent not found with ID: " + agentId));

        if (!agent.getEmail().equalsIgnoreCase(request.getEmail()) &&
                agentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already in use: " + request.getEmail());
        }

        agentMapper.updateEntity(agent, request);
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            agent.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        InsuranceAgent updated = agentRepository.save(agent);
        return agentMapper.toResponse(updated);
    }

    @Override
    public void deleteAgent(Integer agentId) {
        InsuranceAgent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance Agent not found with ID: " + agentId));
        agentRepository.delete(agent);
    }
}