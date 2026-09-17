package com.einsurance.mapper;

import com.einsurance.dto.admin.AdminRequest;
import com.einsurance.dto.admin.AdminResponse;
import com.einsurance.entity.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public Admin toEntity(AdminRequest request) {

        return Admin.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .build();
    }

    public AdminResponse toResponse(Admin admin) {

        return AdminResponse.builder()
                .adminId(admin.getAdminId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .fullName(admin.getFullName())
                .createdAt(admin.getCreatedAt())
                .build();
    }

    public void updateEntity(Admin admin, AdminRequest request) {

        admin.setUsername(request.getUsername());
        admin.setEmail(request.getEmail());
        admin.setFullName(request.getFullName());
    }
}