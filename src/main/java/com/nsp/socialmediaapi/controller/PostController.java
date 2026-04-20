package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.mapper.Page.PageMapper;
import com.nsp.socialmediaapi.mapper.Post.PostDtoMapper;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Post.CreatePostRequest;
import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.service.Post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    private final PageMapper pageMapper;

    private final PostDtoMapper postDtoMapper;

    @PostMapping()
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequest request) {
        Post createdPost = postService.createPost(request);
        PostDto mappedPostDto = postDtoMapper.toDto(createdPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(mappedPostDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<PageResponse<PostDtoSimple>> getAllRecentPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Post> postPage = postService.showAllRecentPosts(page, size);
        Page<PostDtoSimple> postDtoSimplePage = postPage.map(postDtoMapper::toDtoSimple);
        PageResponse<PostDtoSimple> postDtoSimplePageResponse = pageMapper.toPageResponse(postDtoSimplePage);
        return ResponseEntity.ok(postDtoSimplePageResponse);
    }

}