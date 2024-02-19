package com.example.PetProjectSpring.auth.controllers;

import com.example.PetProjectSpring.auth.dto.SignUpDto;
import com.example.PetProjectSpring.auth.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/local/signUp")
    public ResponseEntity signUpLocal(@RequestBody SignUpDto dto) {
        try {
            return ResponseEntity.ok().body(this.authService.signUpLocal(dto));
        } catch (Exception err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }
}
