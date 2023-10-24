package com.example.mayo.journey.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegistrationException extends RuntimeException {

    public RegistrationException(String message) {
        super(message);
    }
}
