package com.einsurance.dto.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceAgentResponse {

    private Integer agentId;

    private String username;

    private String email;

    private String fullName;

    private LocalDateTime createdAt;
}