package com.example.PetProjectSpring.auth.types;

import lombok.Data;

@Data
public class JwtTokens {
    private String refreshToken;
    private String accessToken;
}
