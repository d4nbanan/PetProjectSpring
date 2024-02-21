package com.example.PetProjectSpring.auth.controllers;

import com.example.PetProjectSpring.auth.dto.SignUpDto;
import com.example.PetProjectSpring.auth.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    public void AuthController(
        @Autowired AuthService authService
    ) {
        this.authService = authService;
    }

//    @Autowired
    private AuthService authService;

    @GetMapping("/test")
    public ResponseEntity test() {
        System.out.println("Request");

        return ResponseEntity.ok().body(5);
    }

    @PostMapping("/local/signUp")
    public ResponseEntity signUpLocal(@RequestBody SignUpDto dto, @RequestHeader(value = "User-Agent") String userAgent) {
        System.out.println("Response");

        try {
            return ResponseEntity.ok().body(this.authService.signUpLocal(dto, userAgent));
        } catch (Exception err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }
}
