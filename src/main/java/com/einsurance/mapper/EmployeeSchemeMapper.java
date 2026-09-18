package com.einsurance.mapper;

import com.einsurance.dto.employeescheme.EmployeeSchemeResponse;
import com.einsurance.entity.Employee;
import com.einsurance.entity.EmployeeScheme;
import com.einsurance.entity.Scheme;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSchemeMapper {

    public EmployeeScheme toEntity(Employee employee, Scheme scheme) {
        return EmployeeScheme.builder()
                .employee(employee)
                .scheme(scheme)
                .build();
    }

    public EmployeeSchemeResponse toResponse(EmployeeScheme entity) {
        return EmployeeSchemeResponse.builder()
                .employeeSchemeId(entity.getEmployeeSchemeId())
                .employeeId(entity.getEmployee() != null ? entity.getEmployee().getEmployeeId() : null)
                .employeeName(entity.getEmployee() != null ? entity.getEmployee().getFullName() : null)
                .schemeId(entity.getScheme() != null ? entity.getScheme().getSchemeId() : null)
                .schemeName(entity.getScheme() != null ? entity.getScheme().getSchemeName() : null)
                .build();
    }
}
