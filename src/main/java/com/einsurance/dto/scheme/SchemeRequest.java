package com.einsurance.dto.scheme;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchemeRequest {

    @NotBlank(message = "Scheme name is required")
    @Size(max = 100, message = "Scheme name must not exceed 100 characters")
    private String schemeName;

    @NotBlank(message = "Scheme details are required")
    private String schemeDetails;

    @NotNull(message = "Plan ID is required")
    private Integer planId;
}
