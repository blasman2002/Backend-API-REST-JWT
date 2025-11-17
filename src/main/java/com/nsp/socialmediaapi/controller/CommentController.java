package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.dto.Comment.CommentPostRequest;
import com.nsp.socialmediaapi.service.Comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("{id}/comments")
    public ResponseEntity<CommentDto> commentPost(@PathVariable Long id, @Valid @RequestBody CommentPostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.commentPost(id, request));
    }

    @DeleteMapping("{postId}/comments/{commentId}")
    public ResponseEntity<?> commentDelete(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteCommentFromPost(postId, commentId);
        return ResponseEntity.noContent().build();
    }

}