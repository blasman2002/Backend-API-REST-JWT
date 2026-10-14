package com.nsp.socialmediaapi.service;

import com.nsp.socialmediaapi.service.Auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    private UserDetails userDetails;

    private Authentication authentication;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        String secret_key = Base64.getEncoder().encodeToString("YmVjYW1lbWVhc3VyZWdldHRpbmdsb3ZlbHlhZmZlY3R6ZWJyYWVkdWNhdGlvbmZvcm0=".getBytes());

        ReflectionTestUtils.setField(jwtService, "JWT_SECRET", secret_key);
        ReflectionTestUtils.setField(jwtService, "JWT_EXPIRATION", 3600000L);
        ReflectionTestUtils.setField(jwtService, "JWT_REFRESH", 86400000L);

        userDetails = User.withUsername("user").password("password").roles("USER").build();

        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Test
    void generateAccessToken_shouldSuccessfullyGenerateToken() {
        String token = jwtService.generateAccessToken(authentication);

        assertNotNull(token);
        assertEquals("user", jwtService.getUsernameFromToken(token));
        assertTrue(jwtService.isValidToken(token, userDetails));
    }

    @Test
    void generateRefreshToken_shouldSuccessfullyGenerateToken() {
        String refreshToken = jwtService.generateRefreshToken(authentication);

        assertNotNull(refreshToken);
        assertTrue(jwtService.isValidRefreshToken(refreshToken));
    }

    @Test
    void isValidToken_shouldReturnFalseWhenUsernameDoesNotMatch() {
        String token = jwtService.generateAccessToken(authentication);

        UserDetails otherUser = User.withUsername("otherUser").password("password").roles("USER").build();

        assertFalse(jwtService.isValidToken(token, otherUser));
    }

    @Test
    void isValidRefreshToken_shouldReturnFalseWhenGivenAccessToken() {
        String accessToken = jwtService.generateAccessToken(authentication);

        assertFalse(jwtService.isValidRefreshToken(accessToken));
    }

    @Test
    void isValidRefreshToken_shouldReturnFalseForInvalidToken() {
        assertFalse(jwtService.isValidRefreshToken("bla bla bla token"));
    }

    @Test
    void isValidToken_shouldReturnFalseWhenTokenIsExpired() {
        ReflectionTestUtils.setField(jwtService, "JWT_EXPIRATION", -1000L);

        String token = jwtService.generateAccessToken(authentication);

        assertFalse(jwtService.isValidToken(token, userDetails));
    }

    @Test
    void isValidToken_shouldReturnFalseWhenInvalidSignature() {
        String token = jwtService.generateAccessToken(authentication);

        String secret = Base64.getEncoder().encodeToString("YmVjYW13bW2hc2Vy2Wdld2Rpbmdsb3ZlbHlhZmZlb3R6ZWJyYWbkdWNhdGlabmZvcm0=".getBytes());

        ReflectionTestUtils.setField(jwtService, "JWT_SECRET", secret);

        assertFalse(jwtService.isValidToken(token, userDetails));
    }

}
