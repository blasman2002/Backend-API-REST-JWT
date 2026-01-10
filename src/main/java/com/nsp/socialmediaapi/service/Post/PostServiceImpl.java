package com.nsp.socialmediaapi.service.Post;

import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.PostDoesNotBelongToUserException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.PostNotFoundException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.mapper.Page.PageMapper;
import com.nsp.socialmediaapi.mapper.Post.PostDtoMapper;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Post.CreatePostRequest;
import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.PostRepository;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final PageMapper pageMapper;

    @Override
    @Transactional
    public PostDto createPost(CreatePostRequest request) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(currentUsername).orElseThrow(
                UserNotFoundException::new
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
    public void deletePost(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(currentUsername).orElseThrow(
                UserNotFoundException::new
        );

        Post post = postRepository.findById(id).orElseThrow(
                PostNotFoundException::new
        );

        if (!post.getUser().getId().equals(user.getId())) {
            throw new PostDoesNotBelongToUserException();
        }

        postRepository.delete(post);
    }

    @Override
    @Transactional
    public PageResponse<PostDtoSimple> showUserPosts(Long id, int page, int size) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<PostDtoSimple> postDtoPage = postRepository.findByUserId(id, pageable).map(postDtoMapper::toDtoSimple);

        return pageMapper.toPageResponse(postDtoPage);
    }

    @Override
    @Transactional
    public PageResponse<PostDtoSimple> showAllRecentPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<PostDtoSimple> postDtoPage = postRepository.findAll(pageable).map(postDtoMapper::toDtoSimple);

        return pageMapper.toPageResponse(postDtoPage);
    }

}
