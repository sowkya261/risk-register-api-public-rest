package com.internship.tool.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    private JwtUtil jwtUtil;
    private final String secret = "mysecretkeymysecretkeymysecretkey12";
    private final long expiration = 3600000;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(secret, expiration);
    }

    @Test
    void testGenerateAndValidateToken() {
        String username = "test@demo.com";
        String token = jwtUtil.generateToken(username);
        assertNotNull(token);
        assertEquals(username, jwtUtil.extractUsername(token));
        assertTrue(jwtUtil.isTokenValid(token, username));
    }

    @Test
    void testExpiredToken() throws InterruptedException {
        JwtUtil shortJwtUtil = new JwtUtil(secret, 1);
        String token = shortJwtUtil.generateToken("expired@demo.com");
        Thread.sleep(2);
        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> shortJwtUtil.isTokenValid(token, "expired@demo.com"));
    }
}
