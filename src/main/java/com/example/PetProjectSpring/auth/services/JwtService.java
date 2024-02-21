package com.example.PetProjectSpring.auth.services;

import com.example.PetProjectSpring.auth.types.JwtTokens;
import com.example.PetProjectSpring.auth.types.TokenPayload;
import com.example.PetProjectSpring.auth.dto.TokenPayloadDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import com.example.PetProjectSpring.common.funcs.GetHashFromClass;

import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    private final String SECRET_KEY_ACCESS = "A7bDCnjy6cAn8GmQQyC9NNpoFQNUJFZ47sr0MuMVVEQuLIbMsZ";
    private final String SECRET_KEY_REFRESH = "A7bDCnjy6cAn8GmQQyC9NNpoFQNUJFZ47sr0MuMVVEQuLIbMsZ";


    private final long Default_Token_Lifetime_Access = 7200000; // in ms
    private final long Default_Token_Lifetime_Refresh = 2592000000L; // in ms

    private String generateToken(TokenPayloadDto payload, long lifetime, String secret) throws IllegalAccessException {
        Claims claims = Jwts.claims();
        claims.setSubject(payload.sub);

        Date issuedAt = new Date();
        claims.setIssuedAt(issuedAt);

        Date expiresAt = new Date(issuedAt.getTime() + lifetime);
        claims.setExpiration(expiresAt);

        HashMap<String, String> map = GetHashFromClass.getHash(payload.getClass());
        claims.putAll(map);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public JwtTokens generateTokens(TokenPayloadDto payload) throws IllegalAccessException {
        String accessToken = this.generateToken(payload, this.Default_Token_Lifetime_Access, this.SECRET_KEY_ACCESS);
        String refreshToken = this.generateToken(payload, this.Default_Token_Lifetime_Refresh, this.SECRET_KEY_REFRESH);

        JwtTokens tokens = new JwtTokens();
        tokens.accessToken = accessToken;
        tokens.refreshToken = refreshToken;

        return tokens;
    }

    public TokenPayload validate(String token) {
        return null;
    }
}
