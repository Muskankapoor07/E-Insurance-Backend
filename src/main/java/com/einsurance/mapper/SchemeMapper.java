package com.einsurance.mapper;

import com.einsurance.dto.scheme.SchemeRequest;
import com.einsurance.dto.scheme.SchemeResponse;
import com.einsurance.entity.InsurancePlan;
import com.einsurance.entity.Scheme;
import org.springframework.stereotype.Component;

@Component
public class SchemeMapper {

    public Scheme toEntity(SchemeRequest request, InsurancePlan plan) {
        return Scheme.builder()
                .schemeName(request.getSchemeName())
                .schemeDetails(request.getSchemeDetails())
                .insurancePlan(plan)
                .build();
    }

    public SchemeResponse toResponse(Scheme entity) {
        return SchemeResponse.builder()
                .schemeId(entity.getSchemeId())
                .schemeName(entity.getSchemeName())
                .schemeDetails(entity.getSchemeDetails())
                .planId(entity.getInsurancePlan() != null ? entity.getInsurancePlan().getPlanId() : null)
                .planName(entity.getInsurancePlan() != null ? entity.getInsurancePlan().getPlanName() : null)
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public void updateEntity(Scheme entity, SchemeRequest request, InsurancePlan plan) {
        entity.setSchemeName(request.getSchemeName());
        entity.setSchemeDetails(request.getSchemeDetails());
        entity.setInsurancePlan(plan);
    }
}
