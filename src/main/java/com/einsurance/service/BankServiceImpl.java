package com.einsurance.service;

import com.einsurance.dto.bank.BankRequest;
import com.einsurance.dto.bank.BankResponse;
import com.einsurance.entity.Bank;
import com.einsurance.exception.DuplicateResourceException;
import com.einsurance.exception.ResourceNotFoundException;
import com.einsurance.mapper.BankMapper;
import com.einsurance.repository.BankRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    public BankServiceImpl(BankRepository bankRepository, BankMapper bankMapper) {
        this.bankRepository = bankRepository;
        this.bankMapper = bankMapper;
    }

    @Override
    public BankResponse createBank(BankRequest request) {
        if (bankRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new DuplicateResourceException("Bank account with number '" + request.getAccountNumber() + "' already exists");
        }
        Bank bank = bankMapper.toEntity(request);
        Bank saved = bankRepository.save(bank);
        return bankMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BankResponse getBankById(Integer bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found with ID: " + bankId));
        return bankMapper.toResponse(bank);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankResponse> getAllBanks(Pageable pageable) {
        return bankRepository.findAll(pageable)
                .map(bankMapper::toResponse);
    }

    @Override
    public BankResponse updateBank(Integer bankId, BankRequest request) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found with ID: " + bankId));

        if (!bank.getAccountNumber().equals(request.getAccountNumber()) &&
                bankRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new DuplicateResourceException("Bank account with number '" + request.getAccountNumber() + "' already exists");
        }

        bankMapper.updateEntity(bank, request);
        Bank updated = bankRepository.save(bank);
        return bankMapper.toResponse(updated);
    }

    @Override
    public void deleteBank(Integer bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found with ID: " + bankId));
        bankRepository.delete(bank);
    }
}
