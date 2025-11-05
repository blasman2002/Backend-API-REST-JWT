package com.nsp.redsocial.controller;

import com.nsp.redsocial.service.Like.LikeService;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok("like dado.");
    }

    @DeleteMapping("/posts/{postId}/likes/{userId}")
    public ResponseEntity<?> deleteLike(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.removeLike(postId, userId);
        return ResponseEntity.ok("like retirado.");
    }
}
