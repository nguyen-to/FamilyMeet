package org.example.server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${setTime.accessToken}")
    private long accessTokenMinutes; // tính theo phút

    @Value("${setTime.refreshToken}")
    private long refreshTokenMinutes; // tính theo phút

    //Generate Access Token
    public String generateAccessToken(Authentication authentication, String deviceId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("deviceId", deviceId);
        claims.put("tokenType", "access");
        return generateToken(authentication, accessTokenMinutes, claims);
    }

    //Generate Refresh Token
    public String generateRefreshToken(Authentication authentication, String deviceId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenType", "refresh");
        claims.put("deviceId", deviceId);
        return generateToken(authentication, refreshTokenMinutes, claims);
    }

    //Validate Access Token
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String email = extractEmailFromToken(token);
            return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    //Validate Refresh Token
    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !isTokenExpired(token)
                    && "refresh".equals(claims.get("tokenType"));
        } catch (Exception e) {
            return false;
        }
    }

    //Kiểm tra token hết hạn
    public boolean isTokenExpired(String token) {
        try {
            return extractAllClaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    //Sinh token
    private String generateToken(Authentication authentication, long minutes, Map<String, Object> claims) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + minutes * 60 * 1000);

        return Jwts.builder()
                .header().add("type", "JWT").and()
                .subject(user.getUsername())
                .claims(claims)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSecretKey())
                .compact();
    }

    //Lấy email từ token
    public String extractEmailFromToken(String token) {
        return extractAllClaims(token).getSubject();
    }

    //Lấy deviceId từ token
    public String extractDeviceIdFromToken(String token) {
        return extractAllClaims(token).get("deviceId", String.class);
    }

    //Parse toàn bộ claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Secret key
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
