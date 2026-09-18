package com.einsurance.dto.scheme;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchemeResponse {

    private Integer schemeId;
    private String schemeName;
    private String schemeDetails;
    private Integer planId;
    private String planName;
    private LocalDateTime createdAt;
}
