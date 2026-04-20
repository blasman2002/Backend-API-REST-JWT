package com.nsp.socialmediaapi.service.Post;

import com.nsp.socialmediaapi.model.dto.Post.CreatePostRequest;
import com.nsp.socialmediaapi.model.entity.Post;
import org.springframework.data.domain.Page;

public interface PostService {
    Post findById(Long id);
    Post createPost(CreatePostRequest createPostRequest);
    Page<Post> showUserPosts(Long id, int page, int size);
    void deletePost(Long id);
    Page<Post> showAllRecentPosts(int page, int size);
}
