package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.dto.Comment.CommentNewDto;
import com.nsp.socialmediaapi.service.Comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> commentPost(@PathVariable Long postId, @RequestBody CommentNewDto commentNewDto) {
        return ResponseEntity.ok(commentService.commentPost(postId, commentNewDto));
    }

}
