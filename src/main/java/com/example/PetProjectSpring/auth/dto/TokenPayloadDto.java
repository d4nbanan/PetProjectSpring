package com.example.PetProjectSpring.auth.dto;

import com.example.PetProjectSpring.auth.types.Roles;

public class TokenPayloadDto {
    public String sessionId;
    public Roles roles;
    public String sub;
}
