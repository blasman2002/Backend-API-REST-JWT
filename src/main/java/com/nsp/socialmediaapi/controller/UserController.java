package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.service.Post.PostService;
import com.nsp.socialmediaapi.service.User.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    private final PostService postService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("{id}")
    public ResponseEntity<UserProfileDto> showUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserProfile(id));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("{id}/posts")
    public ResponseEntity<PageResponse<PostDtoSimple>> showUserPosts(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(postService.showUserPosts(id, page, size));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("me")
    public ResponseEntity<?> deleteUserSelf() {
        userService.deleteUserSelf();
        return ResponseEntity.ok("Account has been successfully disabled.");
    }

}
