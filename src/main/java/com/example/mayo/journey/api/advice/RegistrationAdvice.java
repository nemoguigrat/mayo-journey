package com.example.mayo.journey.api.advice;

import com.example.mayo.journey.exception.RegistrationException;
import com.example.mayo.journey.support.exception.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RegistrationAdvice {

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ApiError> handleRegistration(RegistrationException ex) {
        String message = ex.getMessage() == null ? "Ошибка при регистрации пользователя" : ex.getMessage();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiError.of("REGISTRATION_EXCEPTION", message));
    }
}
