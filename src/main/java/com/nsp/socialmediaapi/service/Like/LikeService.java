package com.nsp.socialmediaapi.service.Like;

public interface LikeService {
    void giveLike(Long postId, Long userId);
    void removeLike(Long postId, Long userId);
}
