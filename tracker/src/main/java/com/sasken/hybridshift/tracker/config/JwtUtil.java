package com.sasken.hybridshift.tracker.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 🔑 256-bit secret key (minimum required)
    private static final String SECRET_KEY =
            "hybridshiftsecretkeyhybridshiftsecretkey";

    private final Key key =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // ✅ Extract username from token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ✅ Extract role from token
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // ✅ Check if token expired
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // ✅ Generate JWT with ROLE (USED IN /auth/login)
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // 🔥 IMPORTANT
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 60 * 60 * 1000)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔐 Parse token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
