package com.einsurance.service;

import com.einsurance.dto.admin.AdminRequest;
import com.einsurance.dto.admin.AdminResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    AdminResponse createAdmin(AdminRequest request);

    AdminResponse getAdminById(Integer adminId);

    Page<AdminResponse> getAllAdmins(Pageable pageable);

    AdminResponse updateAdmin(Integer adminId, AdminRequest request);

    void deleteAdmin(Integer adminId);
}