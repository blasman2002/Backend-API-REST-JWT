package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.request.Login.LoginRequestDto;
import com.nsp.socialmediaapi.model.dto.Login.LoginResponseDto;
import com.nsp.socialmediaapi.model.request.Login.RefreshTokenRequest;
import com.nsp.socialmediaapi.model.request.User.CreateUserRequest;
import com.nsp.socialmediaapi.service.Auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserRequest request) {
        authService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("account created.");
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        LoginResponseDto response = authService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

}
