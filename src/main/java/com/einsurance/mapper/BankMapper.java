package com.einsurance.mapper;

import com.einsurance.dto.bank.BankRequest;
import com.einsurance.dto.bank.BankResponse;
import com.einsurance.entity.Bank;
import org.springframework.stereotype.Component;

@Component
public class BankMapper {

    public Bank toEntity(BankRequest request) {
        return Bank.builder()
                .bankName(request.getBankName())
                .branchName(request.getBranchName())
                .ifscCode(request.getIfscCode())
                .accountNumber(request.getAccountNumber())
                .build();
    }

    public BankResponse toResponse(Bank bank) {
        return BankResponse.builder()
                .bankId(bank.getBankId())
                .bankName(bank.getBankName())
                .branchName(bank.getBranchName())
                .ifscCode(bank.getIfscCode())
                .accountNumber(bank.getAccountNumber())
                .createdAt(bank.getCreatedAt())
                .build();
    }

    public void updateEntity(Bank bank, BankRequest request) {
        bank.setBankName(request.getBankName());
        bank.setBranchName(request.getBranchName());
        bank.setIfscCode(request.getIfscCode());
        bank.setAccountNumber(request.getAccountNumber());
    }
}
