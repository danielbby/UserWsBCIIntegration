package com.bci.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class TokenGenerator {
	private final static Properties props = ApplicationProperties.loadProperties();
	private final static String tokenDuration = props.getProperty("tokenDuration");
	
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @SuppressWarnings("deprecation")
	public static String generateToken(String email, String timestamp, String userId) {
        long expirationTimeMillis = System.currentTimeMillis() + Integer.valueOf(tokenDuration); 
        Date expirationDate = new Date(expirationTimeMillis);

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("timestamp", timestamp);
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}