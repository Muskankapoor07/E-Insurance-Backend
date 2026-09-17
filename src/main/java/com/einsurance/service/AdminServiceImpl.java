package com.einsurance.service;

import com.einsurance.dto.admin.AdminRequest;
import com.einsurance.dto.admin.AdminResponse;
import com.einsurance.entity.Admin;
import com.einsurance.mapper.AdminMapper;
import com.einsurance.repository.AdminRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminServiceImpl(
            AdminRepository adminRepository,
            AdminMapper adminMapper
    ) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public AdminResponse createAdmin(AdminRequest request) {

        if (adminRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Admin admin = adminMapper.toEntity(request);

        admin.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        Admin savedAdmin = adminRepository.save(admin);

        return adminMapper.toResponse(savedAdmin);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminResponse getAdminById(Integer adminId) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Admin not found")
                );

        return adminMapper.toResponse(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdminResponse> getAllAdmins(Pageable pageable) {

        return adminRepository.findAll(pageable)
                .map(adminMapper::toResponse);
    }

    @Override
    public AdminResponse updateAdmin(
            Integer adminId,
            AdminRequest request
    ) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Admin not found")
                );

        adminMapper.updateEntity(admin, request);

        Admin updatedAdmin = adminRepository.save(admin);

        return adminMapper.toResponse(updatedAdmin);
    }

    @Override
    public void deleteAdmin(Integer adminId) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Admin not found")
                );

        adminRepository.delete(admin);
    }
}