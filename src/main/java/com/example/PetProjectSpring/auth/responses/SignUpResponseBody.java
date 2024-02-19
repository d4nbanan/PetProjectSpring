package com.example.PetProjectSpring.auth.responses;

import com.example.PetProjectSpring.user.models.User;

public class SignUpResponseBody {
    public String accessToken;
    public String refreshToken;

    public User user;
}
