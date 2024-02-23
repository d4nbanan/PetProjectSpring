package com.example.PetProjectSpring.auth.dto;

import lombok.Data;

import java.util.List;

@Data()
public class TokenPayloadDto {
    public String sessionId;
    public List<String> roles;
    public String sub;
}
