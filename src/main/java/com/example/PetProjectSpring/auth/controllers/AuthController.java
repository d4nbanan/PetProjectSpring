package com.example.PetProjectSpring.auth.controllers;

import com.example.PetProjectSpring.auth.dto.RefreshTokenDto;
import com.example.PetProjectSpring.auth.dto.SignUpDto;
import com.example.PetProjectSpring.auth.services.AuthService;
import com.example.PetProjectSpring.core.annotations.RestErrorHandler;
import com.example.PetProjectSpring.core.exceptions.CoreRestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @RestErrorHandler
    @PreAuthorize("hasRole('ROLE_VIEWER')")
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("Ok");
    }

    @RestErrorHandler
    @PostMapping("/local/signUp")
    public ResponseEntity<?> signUpLocal(@RequestBody SignUpDto dto, @RequestHeader(value = "User-Agent") String userAgent) throws CoreRestException {
        System.out.println(dto);
        System.out.println(userAgent);

        return ResponseEntity.ok().body(this.authService.signUpLocal(dto, userAgent));
    }

    @RestErrorHandler
    @PostMapping("/local/signIn")
    public ResponseEntity<?> signInLocal(@RequestBody SignUpDto dto, @RequestHeader(value = "User-Agent") String userAgent) throws CoreRestException {
        return ResponseEntity.ok().body(this.authService.signInLocal(dto, userAgent));
    }

    @RestErrorHandler
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenDto dto) throws CoreRestException {
        return ResponseEntity.ok().body(this.authService.refreshToken(dto));
    }
}
