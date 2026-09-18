package com.einsurance.service;

import com.einsurance.dto.bank.BankRequest;
import com.einsurance.dto.bank.BankResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankService {
    BankResponse createBank(BankRequest request);
    BankResponse getBankById(Integer bankId);
    Page<BankResponse> getAllBanks(Pageable pageable);
    BankResponse updateBank(Integer bankId, BankRequest request);
    void deleteBank(Integer bankId);
}
