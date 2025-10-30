package com.nsp.redsocial.service.Post;

import com.nsp.redsocial.mapper.Post.PostDtoMapper;
import com.nsp.redsocial.mapper.Post.PostMapper;
import com.nsp.redsocial.model.dto.Post.PostDto;
import com.nsp.redsocial.model.dto.Post.PostDtoSimple;
import com.nsp.redsocial.model.dto.Post.PostNewDto;
import com.nsp.redsocial.model.entity.Post;
import com.nsp.redsocial.model.entity.User;
import com.nsp.redsocial.model.repository.PostRepository;
import com.nsp.redsocial.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostDtoMapper postDtoMapper;

    private final PostMapper postMapper;

    @Override
    @Transactional
    public PostDto save(PostNewDto postNewDto, Long userId) {
        Post p = postMapper.toNewPost(postNewDto);
        User u = userRepository.getUserById(userId);
        p.setUser(u);

        Post saved = postRepository.save(p);

        return postDtoMapper.toDto(saved);
    }

    @Override
    @Transactional
    public List<PostDtoSimple> findAllRecent() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        List<Post> postsFound = postRepository.findAll(sort);
        List<PostDtoSimple> mappedPosts = new ArrayList<>();

        for(Post p : postsFound) {
            PostDtoSimple mappedPost = postDtoMapper.toDtoSimple(p);
            mappedPosts.add(mappedPost);
        }

        return mappedPosts;
    }

    @Override
    public PostDto findById(Long id) {
        Post p = postRepository.findById(id).orElse(null);
        return postDtoMapper.toDto(p);
    }

    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

}
