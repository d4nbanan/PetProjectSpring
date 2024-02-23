package com.example.PetProjectSpring.auth.services;

import com.example.PetProjectSpring.auth.types.JwtTokens;
import com.example.PetProjectSpring.auth.types.TokenPayload;
import com.example.PetProjectSpring.auth.dto.TokenPayloadDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.security.Keys;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class JwtService {
    private final String SECRET_KEY_JWT = "5pAq6zRyX8bC3dV2wS7gN1mK9jF0hL4tUoP6iBvE3nG8xZaQrY7cW2fA";

    private final Duration lifetime_at = Duration.of(2, ChronoUnit.HOURS);
    private final Duration lifetime_rt = Duration.of(30, ChronoUnit.DAYS);

    private String generateToken(TokenPayloadDto payload, long lifetime, String secret) throws Exception {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + lifetime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("sessionId", payload.getSessionId());
        claims.put("roles", payload.getRoles());
        claims.put("sub", payload.getSub());

        claims.put("iat", Math.round(issuedAt.getTime() / 1000));
        claims.put("exp", Math.round(expiresAt.getTime() / 1000));

        return Jwts.builder()
            .setSubject(payload.getSub())
            .setExpiration(expiresAt)
            .setIssuedAt(issuedAt)
            .setClaims(claims)
            .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }

    public JwtTokens generateTokens(TokenPayloadDto payload) throws Exception {
        String accessToken = this.generateToken(payload, this.lifetime_at.toMillis(), this.SECRET_KEY_JWT);
        String refreshToken = this.generateToken(payload, this.lifetime_rt.toMillis(), this.SECRET_KEY_JWT);

        System.out.println(accessToken);

        JwtTokens tokens = new JwtTokens();
        tokens.setAccessToken(accessToken);
        tokens.setRefreshToken(refreshToken);



        return tokens;
    }

    public String getUserId(String token) {
        return (String) this.decodeToken(token).get("sub");
    }

    public List<String> getUserRoles(String token) {
        String key = "roles";
        List<String> roles = (List<String>) this.decodeToken(token).get(key);
        return roles;
    }

    public Map<String, Object> decodeToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(this.SECRET_KEY_JWT.getBytes()))
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public TokenPayload validate(String token) {
        return null;
    }
}
