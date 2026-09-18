package com.einsurance.service;

import com.einsurance.dto.scheme.SchemeRequest;
import com.einsurance.dto.scheme.SchemeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SchemeService {
    SchemeResponse createScheme(SchemeRequest request);
    SchemeResponse getSchemeById(Integer schemeId);
    Page<SchemeResponse> getAllSchemes(Pageable pageable);
    List<SchemeResponse> getSchemesByPlanId(Integer planId);
    SchemeResponse updateScheme(Integer schemeId, SchemeRequest request);
    void deleteScheme(Integer schemeId);
}
