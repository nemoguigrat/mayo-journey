package com.example.mayo.journey.service.impl;

import com.example.mayo.journey.exception.RegistrationException;
import com.example.mayo.journey.service.dto.auth.LoginResponse;
import com.example.mayo.journey.service.dto.auth.RegistrationResponse;
import com.example.mayo.journey.service.dto.auth.AuthRequest;
import com.example.mayo.journey.service.dto.auth.RegisterRequest;
import com.example.mayo.journey.domain.jdbc.User;
import com.example.mayo.journey.repository.jdbc.UserRepository;
import com.example.mayo.journey.service.AuthService;
import com.example.mayo.journey.support.UserStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ApiAuthService implements AuthService {


    AuthenticationProvider apiAuthProvider;

    PasswordEncoder passwordEncoder;

    UserRepository userRepository;

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        User user = userRepository.findUserByEmail(authRequest.getEmail());

        boolean authorize = false;
        // не аутентифицируем пользователя, если его не аппровнули
        // if (user.getStatus() == UserStatus.APPROVED) {
        Authentication authentication = apiAuthProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getId().toString(),
                        authRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        authorize = true;
        //}

        return LoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .authorize(authorize)
                .build();
    }

    @Override
    @Transactional
    public RegistrationResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RegistrationException("Пользователь с таким email уже существует");
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .blocked(false)
                .status(UserStatus.CREATED)
                .registrationDate(LocalDate.now())
                .build();

        userRepository.save(user);

        return RegistrationResponse.builder().id(user.getId()).build();
    }
}
