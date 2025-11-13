package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.service.Like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/{postId}/likes/{userId}")
    public ResponseEntity<?> giveLike(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.giveLike(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("like given");
    }

    @DeleteMapping("/posts/{postId}/likes/{userId}")
    public ResponseEntity<?> deleteLike(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.removeLike(postId, userId);
        return ResponseEntity.ok("like removed");
    }

}
