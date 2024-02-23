package com.example.PetProjectSpring.auth.exceptions;

import com.example.PetProjectSpring.core.exceptions.CoreRestException;
import org.springframework.http.HttpStatus;

public class IncorrectCredentialsException extends CoreRestException {
    public IncorrectCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Bad credentials");
    }
}
