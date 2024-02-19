package com.example.PetProjectSpring.auth.services;

import com.example.PetProjectSpring.auth.dto.SignUpDto;
import com.example.PetProjectSpring.auth.responses.SignUpResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
    public SignUpResponseBody signUpLocal(@RequestBody SignUpDto dto) {

    }
}
