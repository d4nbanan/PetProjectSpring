package com.example.PetProjectSpring.auth.dto;

import java.util.List;

public class TokenPayloadDto {
    public String sessionId;
    public List<String> roles;
    public String sub;
}
