package com.einsurance.mapper;

import com.einsurance.dto.plan.InsurancePlanRequest;
import com.einsurance.dto.plan.InsurancePlanResponse;
import com.einsurance.entity.InsurancePlan;
import org.springframework.stereotype.Component;

@Component
public class InsurancePlanMapper {

    public InsurancePlan toEntity(InsurancePlanRequest request) {
        return InsurancePlan.builder()
                .planName(request.getPlanName())
                .planDetails(request.getPlanDetails())
                .build();
    }

    public InsurancePlanResponse toResponse(InsurancePlan entity) {
        int schemeCount = entity.getSchemes() != null ? entity.getSchemes().size() : 0;
        return InsurancePlanResponse.builder()
                .planId(entity.getPlanId())
                .planName(entity.getPlanName())
                .planDetails(entity.getPlanDetails())
                .totalSchemes(schemeCount)
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public void updateEntity(InsurancePlan entity, InsurancePlanRequest request) {
        entity.setPlanName(request.getPlanName());
        entity.setPlanDetails(request.getPlanDetails());
    }
}
