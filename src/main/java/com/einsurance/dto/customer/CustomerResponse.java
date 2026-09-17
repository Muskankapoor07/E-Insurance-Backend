package com.einsurance.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private Integer customerId;

    private String username;

    private String fullName;

    private String email;

    private String phone;

    private LocalDate dateOfBirth;

    private Integer agentId;

    private LocalDateTime createdAt;
}