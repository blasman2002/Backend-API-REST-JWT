package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.service.Post.PostService;
import com.nsp.socialmediaapi.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    private final PostService postService;

    @GetMapping("{id}")
    public ResponseEntity<UserProfileDto> showUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findProfile(id));
    }

    @GetMapping("{id}/posts")
    public ResponseEntity<List<PostDtoSimple>> showUserPosts(@PathVariable Long id) {
        return ResponseEntity.ok(postService.showUserPosts(id));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser() {
        return ResponseEntity.noContent().build();
    }

}
