package com.einsurance.service;

import com.einsurance.dto.auth.LoginRequest;
import com.einsurance.dto.auth.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}