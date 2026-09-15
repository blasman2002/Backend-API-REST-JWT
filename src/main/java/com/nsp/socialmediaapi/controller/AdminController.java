package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.service.Comment.CommentService;
import com.nsp.socialmediaapi.service.Post.PostService;
import com.nsp.socialmediaapi.service.User.UserService;
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

    @DeleteMapping("posts/{id}")
    public ResponseEntity<?> deletePostFromUser(@PathVariable Long id) {
        postService.deletePostFromUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteUserCommentFromPost(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteUserCommentFromPost(postId, commentId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("users/{id}/ban")
    public ResponseEntity<?> banUser(@PathVariable Long id) {
        userService.banUser(id);
        return ResponseEntity.noContent().build();
    }

}
