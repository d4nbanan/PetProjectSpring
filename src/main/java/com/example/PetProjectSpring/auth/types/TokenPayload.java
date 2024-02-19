package com.example.PetProjectSpring.auth.types;

import com.example.PetProjectSpring.auth.dto.TokenPayloadDto;

import java.util.Date;

public class TokenPayload extends TokenPayloadDto {
    public Date iat;
    public Date exp;
}
