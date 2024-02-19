package com.example.PetProjectSpring.auth.errors;

public class UserAlreadyRegistered extends Exception {
    public UserAlreadyRegistered() {
        super("User already registered by this email");
    }
}
