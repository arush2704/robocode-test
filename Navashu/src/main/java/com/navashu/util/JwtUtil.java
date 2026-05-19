package com.navashu.util;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "your-super-secret-key-min-256-bits-long-for-production-use";
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    public String generateToken(Long userId, String email) {
        // Simple JWT implementation - for production, use proper JWT library
        long currentTime = System.currentTimeMillis();
        long expiryTime = currentTime + EXPIRATION_TIME;
        
        String payload = userId + ":" + email + ":" + expiryTime;
        return Base64.getEncoder().encodeToString(payload.getBytes());
    }

    public boolean validateToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            String[] parts = decoded.split(":");
            if (parts.length != 3) {
                return false;
            }
            long expiryTime = Long.parseLong(parts[2]);
            return expiryTime > System.currentTimeMillis();
        } catch (Exception e) {
            return false;
        }
    }

    public Long extractUserId(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            String[] parts = decoded.split(":");
            return Long.parseLong(parts[0]);
        } catch (Exception e) {
            return null;
        }
    }

    public String extractEmail(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            String[] parts = decoded.split(":");
            return parts[1];
        } catch (Exception e) {
            return null;
        }
    }
}

