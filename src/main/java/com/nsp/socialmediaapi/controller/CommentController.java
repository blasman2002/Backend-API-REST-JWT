package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.request.Comment.CommentPostRequest;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.service.Comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("{id}/comments")
    public ResponseEntity<CommentDto> commentPost(@PathVariable Long id, @Valid @RequestBody CommentPostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.commentPost(id, request));
    }

    @DeleteMapping("{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteCommentFromPost(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteCommentFromPost(postId, commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<PageResponse<CommentDto>> getCommentsFromPost(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(commentService.getAllCommentsFromPost(id, page, size));
    }

}