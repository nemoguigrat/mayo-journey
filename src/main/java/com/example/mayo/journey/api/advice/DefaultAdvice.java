package com.example.mayo.journey.api.advice;

import com.example.mayo.journey.support.exception.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultAdvice {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> internalServer(Throwable ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiError.of("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }
}
