package com.nsp.redsocial.service.Post;

import com.nsp.redsocial.model.dto.Post.PostDto;
import com.nsp.redsocial.model.dto.Post.PostDtoSimple;
import com.nsp.redsocial.model.dto.Post.PostNewDto;

import java.util.List;

public interface PostService {
    PostDto save(PostNewDto postNewDto, Long userId);
    List<PostDtoSimple> findAll();
    PostDto findById(Long id);
    void delete(Long id);
}
