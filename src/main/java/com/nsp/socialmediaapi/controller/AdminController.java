package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.service.Comment.CommentService;
import com.nsp.socialmediaapi.service.Post.PostService;
import com.nsp.socialmediaapi.service.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    private final PostService postService;

    private final CommentService commentService;

    private final UserService userService;

    @Operation(
            summary = "Delete a post",
            description = "deletes an user post by its id. Only active administrators can perform this operation."
    )
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("posts/{id}")
    public ResponseEntity<?> deletePostFromUser(@PathVariable Long id) {
        postService.deletePostFromUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete a comment",
            description = "deletes an user comment by its id. Only active administrators can perform this operation."
    )
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("comments/{id}")
    public ResponseEntity<?> deleteUserCommentFromPost(@PathVariable Long id) {
        commentService.deleteUserCommentFromPost(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Ban a user",
            description = "changes user status to BANNED. Only active administrators can perform this operation."
    )
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("users/{id}/ban")
    public ResponseEntity<?> banUser(@PathVariable Long id) {
        userService.banUser(id);
        return ResponseEntity.noContent().build();
    }

}
