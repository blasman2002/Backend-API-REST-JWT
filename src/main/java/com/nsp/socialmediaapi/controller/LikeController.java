package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.service.Like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("{id}/likes")
    public ResponseEntity<?> giveLike(@PathVariable Long id) {
        likeService.giveLikeToPost(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("like given");
    }

    @DeleteMapping("{id}/likes")
    public ResponseEntity<?> deleteLike(@PathVariable Long id) {
        likeService.removeLikeFromPost(id);
        return ResponseEntity.ok("like removed");
    }

}
