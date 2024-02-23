package com.example.PetProjectSpring.auth.responses;

import com.example.PetProjectSpring.auth.types.JwtTokens;
import com.example.PetProjectSpring.user.models.User;

public class SignUpResponse {
    public JwtTokens tokens;

    public User user;
}
