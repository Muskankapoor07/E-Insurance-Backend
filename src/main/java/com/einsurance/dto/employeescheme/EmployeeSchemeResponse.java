package com.einsurance.dto.employeescheme;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeSchemeResponse {

    private Integer employeeSchemeId;
    private Integer employeeId;
    private String employeeName;
    private Integer schemeId;
    private String schemeName;
}
