package com.nsp.redsocial.service.Post;

import com.nsp.redsocial.model.dto.Post.PostDto;
import com.nsp.redsocial.model.dto.Post.PostDtoSimple;
import com.nsp.redsocial.model.dto.Post.PostNewDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostNewDto postNewDto, Long userId);
    List<PostDtoSimple> loadAllRecentPosts();
    void deletePost(Long id);
    PostDto showPost(Long id);
}
