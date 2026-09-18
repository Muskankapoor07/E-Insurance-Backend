package com.einsurance.service;

import com.einsurance.dto.plan.InsurancePlanRequest;
import com.einsurance.dto.plan.InsurancePlanResponse;
import com.einsurance.entity.InsurancePlan;
import com.einsurance.exception.DuplicateResourceException;
import com.einsurance.exception.ResourceNotFoundException;
import com.einsurance.mapper.InsurancePlanMapper;
import com.einsurance.repository.InsurancePlanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InsurancePlanServiceImpl implements InsurancePlanService {

    private final InsurancePlanRepository planRepository;
    private final InsurancePlanMapper planMapper;

    public InsurancePlanServiceImpl(InsurancePlanRepository planRepository, InsurancePlanMapper planMapper) {
        this.planRepository = planRepository;
        this.planMapper = planMapper;
    }

    @Override
    public InsurancePlanResponse createPlan(InsurancePlanRequest request) {
        if (planRepository.existsByPlanNameIgnoreCase(request.getPlanName())) {
            throw new DuplicateResourceException("Insurance Plan with name '" + request.getPlanName() + "' already exists");
        }
        InsurancePlan plan = planMapper.toEntity(request);
        InsurancePlan saved = planRepository.save(plan);
        return planMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public InsurancePlanResponse getPlanById(Integer planId) {
        InsurancePlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance Plan not found with ID: " + planId));
        return planMapper.toResponse(plan);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InsurancePlanResponse> getAllPlans(Pageable pageable) {
        return planRepository.findAll(pageable)
                .map(planMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InsurancePlanResponse> getAllPlansList() {
        return planRepository.findAll().stream()
                .map(planMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InsurancePlanResponse updatePlan(Integer planId, InsurancePlanRequest request) {
        InsurancePlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance Plan not found with ID: " + planId));

        if (!plan.getPlanName().equalsIgnoreCase(request.getPlanName()) &&
                planRepository.existsByPlanNameIgnoreCase(request.getPlanName())) {
            throw new DuplicateResourceException("Insurance Plan with name '" + request.getPlanName() + "' already exists");
        }

        planMapper.updateEntity(plan, request);
        InsurancePlan updated = planRepository.save(plan);
        return planMapper.toResponse(updated);
    }

    @Override
    public void deletePlan(Integer planId) {
        InsurancePlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance Plan not found with ID: " + planId));
        planRepository.delete(plan);
    }
}
