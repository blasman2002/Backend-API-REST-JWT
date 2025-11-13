package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.dto.Comment.CommentNewDto;
import com.nsp.socialmediaapi.service.Comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> commentPost(@PathVariable Long postId, @Valid @RequestBody CommentNewDto commentNewDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.commentPost(postId, commentNewDto));
    }

}