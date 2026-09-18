package com.einsurance.controller;

import com.einsurance.dto.scheme.SchemeRequest;
import com.einsurance.dto.scheme.SchemeResponse;
import com.einsurance.service.SchemeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schemes")
public class SchemeController {

    private final SchemeService schemeService;

    public SchemeController(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @PostMapping
    public ResponseEntity<SchemeResponse> createScheme(
            @Valid @RequestBody SchemeRequest request) {
        SchemeResponse response = schemeService.createScheme(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{schemeId}")
    public ResponseEntity<SchemeResponse> getSchemeById(@PathVariable Integer schemeId) {
        SchemeResponse response = schemeService.getSchemeById(schemeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<SchemeResponse>> getAllSchemes(Pageable pageable) {
        Page<SchemeResponse> response = schemeService.getAllSchemes(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/plan/{planId}")
    public ResponseEntity<List<SchemeResponse>> getSchemesByPlanId(@PathVariable Integer planId) {
        List<SchemeResponse> response = schemeService.getSchemesByPlanId(planId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{schemeId}")
    public ResponseEntity<SchemeResponse> updateScheme(
            @PathVariable Integer schemeId,
            @Valid @RequestBody SchemeRequest request) {
        SchemeResponse response = schemeService.updateScheme(schemeId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{schemeId}")
    public ResponseEntity<Void> deleteScheme(@PathVariable Integer schemeId) {
        schemeService.deleteScheme(schemeId);
        return ResponseEntity.noContent().build();
    }
}
