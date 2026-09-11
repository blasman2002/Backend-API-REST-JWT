package com.nsp.socialmediaapi.service.Post;

import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Post.CreatePostRequest;
import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.entity.Post;

public interface PostService {
    Post findById(Long id);
    PostDto findPostDtoById(Long id);
    PostDto createPost(CreatePostRequest createPostRequest);
    PageResponse<PostDtoSimple> showUserPosts(Long id, int page, int size);
    void deletePost(Long id);
    PageResponse<PostDtoSimple> showAllRecentPosts(int page, int size);
}
