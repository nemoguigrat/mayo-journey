package com.example.mayo.journey.support.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Value
@Builder
public class ApiError {

    String code;

    String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String field;

    public static ApiError of(String code, String message) {
        return ApiError.builder()
                .message(message)
                .code(code)
                .build();
    }
}
