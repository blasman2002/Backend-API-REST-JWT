package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.request.Post.CreatePostRequest;
import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.service.Post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping()
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOwnPost(@PathVariable Long id) {
        postService.deleteOwnPost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<PageResponse<PostDtoSimple>> getAllRecentPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(postService.showAllRecentPosts(page,size));
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findPostDtoById(id));
    }

}