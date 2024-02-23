package com.example.PetProjectSpring.auth.exceptions;

import com.example.PetProjectSpring.core.exceptions.CoreRestException;
import org.springframework.http.HttpStatus;

public class UserAlreadyRegistered extends CoreRestException {
    public UserAlreadyRegistered() {
        super(HttpStatus.BAD_REQUEST, "User already registered by this email");
    }
}
