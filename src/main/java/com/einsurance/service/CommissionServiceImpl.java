package com.einsurance.service;

import com.einsurance.dto.commission.CommissionCalculationResponse;
import com.einsurance.dto.commission.CommissionRequest;
import com.einsurance.dto.commission.CommissionResponse;
import com.einsurance.entity.Commission;
import com.einsurance.entity.InsuranceAgent;
import com.einsurance.entity.Policy;
import com.einsurance.exception.DuplicateResourceException;
import com.einsurance.exception.ResourceNotFoundException;
import com.einsurance.mapper.CommissionMapper;
import com.einsurance.repository.CommissionRepository;
import com.einsurance.repository.InsuranceAgentRepository;
import com.einsurance.repository.PolicyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommissionServiceImpl implements CommissionService {

    private final CommissionRepository commissionRepository;
    private final InsuranceAgentRepository agentRepository;
    private final PolicyRepository policyRepository;
    private final CommissionMapper commissionMapper;

    public CommissionServiceImpl(CommissionRepository commissionRepository,
                                 InsuranceAgentRepository agentRepository,
                                 PolicyRepository policyRepository,
                                 CommissionMapper commissionMapper) {
        this.commissionRepository = commissionRepository;
        this.agentRepository = agentRepository;
        this.policyRepository = policyRepository;
        this.commissionMapper = commissionMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public CommissionCalculationResponse calculateCommissionForAgent(Integer agentId, Double commissionPercentage) {
        InsuranceAgent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance Agent not found with ID: " + agentId));

        double rate = commissionPercentage != null && commissionPercentage > 0 ? commissionPercentage : 10.0;
        BigDecimal rateMultiplier = BigDecimal.valueOf(rate / 100.0);

        List<Policy> agentPolicies = policyRepository.findByCustomer_InsuranceAgent_AgentId(agentId);

        BigDecimal totalPremiumVolume = BigDecimal.ZERO;
        BigDecimal totalCommission = BigDecimal.ZERO;
        List<CommissionCalculationResponse.AgentPolicyCommissionDetail> details = new ArrayList<>();

        for (Policy p : agentPolicies) {
            totalPremiumVolume = totalPremiumVolume.add(p.getPremium());
            BigDecimal itemCommission = p.getPremium().multiply(rateMultiplier).setScale(2, RoundingMode.HALF_UP);
            totalCommission = totalCommission.add(itemCommission);

            boolean alreadyRecorded = commissionRepository.existsByPolicy_PolicyId(p.getPolicyId());

            details.add(CommissionCalculationResponse.AgentPolicyCommissionDetail.builder()
                    .policyId(p.getPolicyId())
                    .customerId(p.getCustomer() != null ? p.getCustomer().getCustomerId() : null)
                    .customerName(p.getCustomer() != null ? p.getCustomer().getFullName() : null)
                    .schemeName(p.getScheme() != null ? p.getScheme().getSchemeName() : null)
                    .premium(p.getPremium())
                    .dateIssued(p.getDateIssued())
                    .calculatedCommission(itemCommission)
                    .alreadyRecorded(alreadyRecorded)
                    .build());
        }

        return CommissionCalculationResponse.builder()
                .agentId(agent.getAgentId())
                .agentName(agent.getFullName())
                .agentEmail(agent.getEmail())
                .totalPoliciesSold(agentPolicies.size())
                .totalPremiumVolume(totalPremiumVolume)
                .commissionPercentage(BigDecimal.valueOf(rate))
                .totalCommissionPayable(totalCommission)
                .policies(details)
                .build();
    }

    @Override
    public CommissionResponse recordCommission(CommissionRequest request) {
        if (commissionRepository.existsByPolicy_PolicyId(request.getPolicyId())) {
            throw new DuplicateResourceException("Commission has already been recorded for Policy ID: " + request.getPolicyId());
        }

        InsuranceAgent agent = agentRepository.findById(request.getAgentId())
                .orElseThrow(() -> new ResourceNotFoundException("Insurance Agent not found with ID: " + request.getAgentId()));

        Policy policy = policyRepository.findById(request.getPolicyId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with ID: " + request.getPolicyId()));

        Commission commission = commissionMapper.toEntity(request, agent, policy);
        Commission saved = commissionRepository.save(commission);
        return commissionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommissionResponse> getCommissionsByAgentId(Integer agentId, Pageable pageable) {
        if (!agentRepository.existsById(agentId)) {
            throw new ResourceNotFoundException("Insurance Agent not found with ID: " + agentId);
        }
        return commissionRepository.findByInsuranceAgent_AgentId(agentId, pageable)
                .map(commissionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommissionResponse> getAllCommissions(Pageable pageable) {
        return commissionRepository.findAll(pageable)
                .map(commissionMapper::toResponse);
    }
}
