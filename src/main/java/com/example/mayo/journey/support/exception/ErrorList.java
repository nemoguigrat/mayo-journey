package com.example.mayo.journey.support.exception;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ErrorList<T extends ApiError> {

    @Singular
    List<T> errors;
}
