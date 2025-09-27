package com.nsp.redsocial.controller;

import com.nsp.redsocial.model.dto.User.UserDto;
import com.nsp.redsocial.model.dto.User.UserNewDto;
import com.nsp.redsocial.model.dto.User.UserProfileDto;
import com.nsp.redsocial.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("users")
    public ResponseEntity<UserDto> register(@RequestBody UserNewDto userNewDto) {
        return ResponseEntity.ok(userService.save(userNewDto));
    }

    @GetMapping("users/{username}/profile")
    public ResponseEntity<UserProfileDto> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

}
