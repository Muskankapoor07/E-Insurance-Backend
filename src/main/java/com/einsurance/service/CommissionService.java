package com.einsurance.service;

import com.einsurance.dto.commission.CommissionCalculationResponse;
import com.einsurance.dto.commission.CommissionRequest;
import com.einsurance.dto.commission.CommissionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommissionService {
    CommissionCalculationResponse calculateCommissionForAgent(Integer agentId, Double commissionPercentage);
    CommissionResponse recordCommission(CommissionRequest request);
    Page<CommissionResponse> getCommissionsByAgentId(Integer agentId, Pageable pageable);
    Page<CommissionResponse> getAllCommissions(Pageable pageable);
}
