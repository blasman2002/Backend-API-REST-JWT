package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.service.Comment.CommentService;
import com.nsp.socialmediaapi.service.Post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    private final PostService postService;

    private final CommentService commentService;

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

}
