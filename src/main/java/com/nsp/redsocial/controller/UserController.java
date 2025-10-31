package com.nsp.redsocial.controller;

import com.nsp.redsocial.exception.ResourceNotFoundException;
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
        return ResponseEntity.ok(userService.registerUser(userNewDto));
    }

    @GetMapping("users/{id}/profile")
    public ResponseEntity<UserProfileDto> showProfile(@PathVariable Long id) {
        UserProfileDto dtoProfile = userService.findProfile(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("user not found")
                );

        return ResponseEntity.ok(dtoProfile);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
