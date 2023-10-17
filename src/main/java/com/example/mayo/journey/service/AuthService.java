package com.example.mayo.journey.service;

import com.example.mayo.journey.service.dto.auth.AuthRequest;
import com.example.mayo.journey.service.dto.auth.LoginResponse;
import com.example.mayo.journey.service.dto.auth.RegisterRequest;
import com.example.mayo.journey.service.dto.auth.RegistrationResponse;

public interface AuthService {

    LoginResponse login(AuthRequest authRequest);

    RegistrationResponse register(RegisterRequest registerRequest);
}
