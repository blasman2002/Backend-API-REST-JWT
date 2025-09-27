package com.nsp.redsocial.controller;

import com.nsp.redsocial.model.dto.Post.PostDto;
import com.nsp.redsocial.model.dto.Post.PostNewDto;
import com.nsp.redsocial.service.Post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<PostDto> createPost(@PathVariable Long userId, @RequestBody PostNewDto postNewDto) {
        return ResponseEntity.ok(postService.save(postNewDto, userId));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id){
        return ResponseEntity.ok(postService.findById(id));
    }

}
