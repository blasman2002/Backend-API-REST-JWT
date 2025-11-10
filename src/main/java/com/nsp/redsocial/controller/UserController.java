package com.nsp.redsocial.controller;

import com.nsp.redsocial.model.dto.User.UserDto;
import com.nsp.redsocial.model.dto.User.UserNewDto;
import com.nsp.redsocial.model.dto.User.UserProfileDto;
import com.nsp.redsocial.service.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("users")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserNewDto userNewDto) {
        UserDto user = userService.registerUser(userNewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("users/{id}/profile")
    public ResponseEntity<UserProfileDto> showProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findProfile(id));
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
