package com.nsp.socialmediaapi.service.Post;

import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.dto.Post.CreatePostRequest;

import java.util.List;

public interface PostService {
    PostDto createPost(CreatePostRequest createPostRequest, Long userId);
    List<PostDtoSimple> loadAllRecentPosts();
    void deletePost(Long id);
    PostDto showPost(Long id);
}
