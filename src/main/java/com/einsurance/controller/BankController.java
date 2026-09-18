package com.einsurance.controller;

import com.einsurance.dto.bank.BankRequest;
import com.einsurance.dto.bank.BankResponse;
import com.einsurance.service.BankService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping
    public ResponseEntity<BankResponse> createBank(
            @Valid @RequestBody BankRequest request) {
        BankResponse response = bankService.createBank(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{bankId}")
    public ResponseEntity<BankResponse> getBankById(@PathVariable Integer bankId) {
        BankResponse response = bankService.getBankById(bankId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<BankResponse>> getAllBanks(Pageable pageable) {
        Page<BankResponse> response = bankService.getAllBanks(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bankId}")
    public ResponseEntity<BankResponse> updateBank(
            @PathVariable Integer bankId,
            @Valid @RequestBody BankRequest request) {
        BankResponse response = bankService.updateBank(bankId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bankId}")
    public ResponseEntity<Void> deleteBank(@PathVariable Integer bankId) {
        bankService.deleteBank(bankId);
        return ResponseEntity.noContent().build();
    }
}
