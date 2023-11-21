package com.example.mayo.journey.api.advice;

import com.example.mayo.journey.exception.NotFoundException;
import com.example.mayo.journey.support.exception.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class NotFoundAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> internalServer(NotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiError.of("NOT_FOUND", HttpStatus.NOT_FOUND.getReasonPhrase()));
    }
}
