package com.nsp.socialmediaapi.service.Post;

import com.nsp.socialmediaapi.exception.customExceptions.ResourceNotFoundException;
import com.nsp.socialmediaapi.mapper.Post.PostDtoMapper;
import com.nsp.socialmediaapi.mapper.Post.PostMapper;
import com.nsp.socialmediaapi.model.dto.Post.CreatePostRequest;
import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.PostRepository;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public PostDto createPost(CreatePostRequest request) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(currentUsername).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );

        Post post = new Post();
        post.setUser(user);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        Post savedPost = postRepository.save(post);

        return postDtoMapper.toDto(savedPost);
    }

    @Override
    @Transactional
    public List<PostDtoSimple> showUserPosts(Long id) {
        List<Post> obtainedPostsList = postRepository.findByUserId(id);
        List<PostDtoSimple> postDtoList = new ArrayList<>();

        obtainedPostsList.forEach(post -> {
            PostDtoSimple mappedDto = postDtoMapper.toDtoSimple(post);
            postDtoList.add(mappedDto);
        });

        return postDtoList;
    }

}
