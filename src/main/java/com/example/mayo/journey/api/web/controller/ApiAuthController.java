package com.example.mayo.journey.api.web.controller;

import com.example.mayo.journey.service.dto.auth.AuthRequest;
import com.example.mayo.journey.service.dto.auth.RegisterRequest;
import com.example.mayo.journey.service.dto.auth.LoginResponse;
import com.example.mayo.journey.service.dto.auth.RegistrationResponse;
import com.example.mayo.journey.service.AuthService;
import com.example.mayo.journey.support.api.WebApi;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static lombok.AccessLevel.PRIVATE;

@WebApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ApiAuthController {

    AuthService apiAuthService;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody AuthRequest authRequest) {
        return apiAuthService.login(authRequest);
    }

    @PostMapping("/auth/register")
    public RegistrationResponse register(@RequestBody RegisterRequest authRequest) {
        return apiAuthService.register(authRequest);
    }
}
