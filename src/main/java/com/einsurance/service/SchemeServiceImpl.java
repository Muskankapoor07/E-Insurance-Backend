package com.einsurance.service;

import com.einsurance.dto.scheme.SchemeRequest;
import com.einsurance.dto.scheme.SchemeResponse;
import com.einsurance.entity.InsurancePlan;
import com.einsurance.entity.Scheme;
import com.einsurance.exception.DuplicateResourceException;
import com.einsurance.exception.ResourceNotFoundException;
import com.einsurance.mapper.SchemeMapper;
import com.einsurance.repository.InsurancePlanRepository;
import com.einsurance.repository.SchemeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SchemeServiceImpl implements SchemeService {

    private final SchemeRepository schemeRepository;
    private final InsurancePlanRepository planRepository;
    private final SchemeMapper schemeMapper;

    public SchemeServiceImpl(SchemeRepository schemeRepository,
                             InsurancePlanRepository planRepository,
                             SchemeMapper schemeMapper) {
        this.schemeRepository = schemeRepository;
        this.planRepository = planRepository;
        this.schemeMapper = schemeMapper;
    }

    @Override
    public SchemeResponse createScheme(SchemeRequest request) {
        if (schemeRepository.existsBySchemeNameIgnoreCase(request.getSchemeName())) {
            throw new DuplicateResourceException("Scheme with name '" + request.getSchemeName() + "' already exists");
        }

        InsurancePlan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Insurance Plan not found with ID: " + request.getPlanId()));

        Scheme scheme = schemeMapper.toEntity(request, plan);
        Scheme saved = schemeRepository.save(scheme);
        return schemeMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SchemeResponse getSchemeById(Integer schemeId) {
        Scheme scheme = schemeRepository.findById(schemeId)
                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with ID: " + schemeId));
        return schemeMapper.toResponse(scheme);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SchemeResponse> getAllSchemes(Pageable pageable) {
        return schemeRepository.findAll(pageable)
                .map(schemeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchemeResponse> getSchemesByPlanId(Integer planId) {
        if (!planRepository.existsById(planId)) {
            throw new ResourceNotFoundException("Insurance Plan not found with ID: " + planId);
        }
        return schemeRepository.findByInsurancePlan_PlanId(planId).stream()
                .map(schemeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SchemeResponse updateScheme(Integer schemeId, SchemeRequest request) {
        Scheme scheme = schemeRepository.findById(schemeId)
                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with ID: " + schemeId));

        InsurancePlan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Insurance Plan not found with ID: " + request.getPlanId()));

        if (!scheme.getSchemeName().equalsIgnoreCase(request.getSchemeName()) &&
                schemeRepository.existsBySchemeNameIgnoreCase(request.getSchemeName())) {
            throw new DuplicateResourceException("Scheme with name '" + request.getSchemeName() + "' already exists");
        }

        schemeMapper.updateEntity(scheme, request, plan);
        Scheme updated = schemeRepository.save(scheme);
        return schemeMapper.toResponse(updated);
    }

    @Override
    public void deleteScheme(Integer schemeId) {
        Scheme scheme = schemeRepository.findById(schemeId)
                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with ID: " + schemeId));
        schemeRepository.delete(scheme);
    }
}
