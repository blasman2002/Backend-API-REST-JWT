package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.dto.Post.CreatePostRequest;
import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.service.Post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping()
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequest request) {
        PostDto createdPost = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }
}
