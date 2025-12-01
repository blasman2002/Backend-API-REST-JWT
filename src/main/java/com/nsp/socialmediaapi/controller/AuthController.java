package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.dto.Login.LoginRequestDto;
import com.nsp.socialmediaapi.model.dto.Login.LoginResponseDto;
import com.nsp.socialmediaapi.model.dto.Login.RefreshTokenRequest;
import com.nsp.socialmediaapi.model.dto.User.CreateUserRequest;
import com.nsp.socialmediaapi.service.Auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    @PostMapping("auth/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("auth/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("auth/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        LoginResponseDto response = authService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

}
