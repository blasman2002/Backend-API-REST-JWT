package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.dto.Login.LoginRequestDto;
import com.nsp.socialmediaapi.model.dto.Login.LoginResponseDto;
import com.nsp.socialmediaapi.model.dto.User.CreateUserRequest;
import com.nsp.socialmediaapi.service.other.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("auth/register")
    public ResponseEntity<?> registerUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto loginResponseDto = authService.login(request);
        return ResponseEntity.ok(loginResponseDto);
    }

}
