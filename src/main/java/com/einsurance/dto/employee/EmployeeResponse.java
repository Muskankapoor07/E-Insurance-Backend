package com.einsurance.dto.employee;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    private Integer employeeId;

    private String username;

    private String email;

    private String fullName;

    private String role;

    private LocalDateTime createdAt;
}