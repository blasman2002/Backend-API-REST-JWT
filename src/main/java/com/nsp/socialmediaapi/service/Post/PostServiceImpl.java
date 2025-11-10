package com.nsp.socialmediaapi.service.Post;

import com.nsp.socialmediaapi.exception.ResourceNotFoundException;
import com.nsp.socialmediaapi.mapper.Post.PostDtoMapper;
import com.nsp.socialmediaapi.mapper.Post.PostMapper;
import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.dto.Post.PostNewDto;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.PostRepository;
import com.nsp.socialmediaapi.model.repository.UserRepository;
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
    public PostDto createPost(PostNewDto postNewDto, Long userId) {
        Post p = postMapper.toNewPost(postNewDto);
        User u = userRepository.getUserById(userId);
        p.setUser(u);

        Post saved = postRepository.save(p);

        return postDtoMapper.toDto(saved);
    }

    @Override
    @Transactional
    public List<PostDtoSimple> loadAllRecentPosts() {
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
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PostDto showPost(Long id) {
        Post p = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found")
        );

        return postDtoMapper.toDto(p);
    }

}
