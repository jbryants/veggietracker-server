package com.example.veggietracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtProvider {

    @Value("${secretKey:fallbackInCaseSecretKeyNotFoundInEnv}")
    private String secretKey;

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        final long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date(now))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            // A signed JWT is known as a JWS (JSON Web Signature).
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            System.out.println(e);
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token) + "\n" + e);
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims body = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return body.getSubject();
    }
}
