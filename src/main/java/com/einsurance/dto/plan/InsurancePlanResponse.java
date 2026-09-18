package com.einsurance.dto.plan;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsurancePlanResponse {

    private Integer planId;
    private String planName;
    private String planDetails;
    private Integer totalSchemes;
    private LocalDateTime createdAt;
}
