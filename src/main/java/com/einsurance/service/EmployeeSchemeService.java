package com.einsurance.service;

import com.einsurance.dto.employeescheme.EmployeeSchemeRequest;
import com.einsurance.dto.employeescheme.EmployeeSchemeResponse;

import java.util.List;

public interface EmployeeSchemeService {
    EmployeeSchemeResponse assignSchemeToEmployee(EmployeeSchemeRequest request);
    List<EmployeeSchemeResponse> getSchemesByEmployeeId(Integer employeeId);
    List<EmployeeSchemeResponse> getEmployeesBySchemeId(Integer schemeId);
    void removeSchemeFromEmployee(Integer employeeId, Integer schemeId);
}
