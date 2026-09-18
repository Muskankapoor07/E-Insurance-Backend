package com.einsurance.dto.bank;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankResponse {

    private Integer bankId;
    private String bankName;
    private String branchName;
    private String ifscCode;
    private String accountNumber;
    private LocalDateTime createdAt;
}
