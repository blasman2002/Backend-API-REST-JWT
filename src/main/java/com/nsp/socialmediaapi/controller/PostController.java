package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.dto.Post.PostNewDto;
import com.nsp.socialmediaapi.service.Post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("users/{id}/posts")
    public ResponseEntity<PostDto> createPost(@PathVariable Long id, @Valid @RequestBody PostNewDto postNewDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postNewDto, id));
    }

    @DeleteMapping("posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok("post deleted");
    }

    @GetMapping("posts")
    public ResponseEntity<List<PostDtoSimple>> getAllPosts(){
        return ResponseEntity.ok(postService.loadAllRecentPosts());
    }

    @GetMapping("posts/{id}")
    public ResponseEntity<PostDto> showPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.showPost(id));
    }

}
