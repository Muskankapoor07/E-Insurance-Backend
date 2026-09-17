package com.einsurance.controller;

import com.einsurance.dto.admin.AdminRequest;
import com.einsurance.dto.admin.AdminResponse;
import com.einsurance.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<AdminResponse> createAdmin(
            @Valid @RequestBody AdminRequest request
    ) {
        AdminResponse response = adminService.createAdmin(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminResponse> getAdminById(
            @PathVariable Integer adminId
    ) {
        AdminResponse response = adminService.getAdminById(adminId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<AdminResponse>> getAllAdmins(
            Pageable pageable
    ) {
        Page<AdminResponse> response = adminService.getAllAdmins(pageable);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<AdminResponse> updateAdmin(
            @PathVariable Integer adminId,
            @Valid @RequestBody AdminRequest request
    ) {
        AdminResponse response = adminService.updateAdmin(adminId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(
            @PathVariable Integer adminId
    ) {
        adminService.deleteAdmin(adminId);

        return ResponseEntity.noContent().build();
    }
}