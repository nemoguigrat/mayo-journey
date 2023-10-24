package com.example.mayo.journey.api.advice;

import com.example.mayo.journey.support.exception.ApiError;
import com.example.mayo.journey.support.exception.ErrorList;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE) // в данный момент это костыль, в дальнейшем завезу нормальный exceptionResolver
public class BindException {

    @ExceptionHandler(org.springframework.validation.BindException.class)
    public ResponseEntity<ErrorList<ApiError>> handleBindException(org.springframework.validation.BindException ex) {
        List<ObjectError> exErrors = ex.getAllErrors();

        List<ApiError> errors = new ArrayList<>();
        for (ObjectError error : exErrors) {
            String field = error instanceof FieldError ? ((FieldError) error).getField() : null;
            errors.add(ApiError.builder()
                    .code(error.getCode())
                    .message(error.getDefaultMessage())
                    .field(field)
                    .build());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorList.builder()
                        .errors(errors)
                        .build());
    }
}
