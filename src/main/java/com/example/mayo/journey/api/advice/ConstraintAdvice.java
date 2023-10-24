package com.example.mayo.journey.api.advice;

import com.example.mayo.journey.support.exception.ErrorList;
import com.example.mayo.journey.support.exception.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ConstraintAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorList<ApiError>> handleConstraintViolation(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        List<ApiError> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : violations) {
            ApiError.of("CONSTRAINT_EXCEPTION", violation.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorList.builder()
                        .errors(errors)
                        .build());
    }
}
