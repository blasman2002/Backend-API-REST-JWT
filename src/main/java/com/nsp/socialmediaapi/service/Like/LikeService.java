package com.nsp.socialmediaapi.service.Like;

public interface LikeService {
    void giveLikeToPost(Long id);
    void removeLikeFromPost(Long id);
}
