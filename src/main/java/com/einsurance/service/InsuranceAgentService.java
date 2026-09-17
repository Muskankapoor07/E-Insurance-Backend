package com.einsurance.service;

import com.einsurance.dto.agent.InsuranceAgentRequest;
import com.einsurance.dto.agent.InsuranceAgentResponse;

public interface InsuranceAgentService {

    InsuranceAgentResponse registerAgent(InsuranceAgentRequest request);
}