package com.nsp.socialmediaapi.service.Post;

import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.PostDoesNotBelongToUserException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.PostNotFoundException;
import com.nsp.socialmediaapi.mapper.Page.PageMapper;
import com.nsp.socialmediaapi.mapper.Post.PostDtoMapper;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Post.CreatePostRequest;
import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.PostRepository;
import com.nsp.socialmediaapi.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    private final PostDtoMapper postDtoMapper;

    private final PageMapper pageMapper;

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(
                PostNotFoundException::new
        );
    }

    @Override
    @Transactional
    public PostDto findPostDtoById(Long id) {
        Post post = findById(id);

        return postDtoMapper.toDto(post);
    }

    @Override
    @PreAuthorize("@userSecurityService.isActive(authentication)")
    @Transactional
    public PostDto createPost(CreatePostRequest request) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUsername(currentUsername);

        Post post = new Post();
        post.setUser(user);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        postRepository.save(post);

        return postDtoMapper.toDto(post);
    }

    @Override
    @Transactional
    public void deleteOwnPost(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUsername(currentUsername);

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
        userService.findById(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> userPosts = postRepository.findByUserId(id, pageable);
        Page<PostDtoSimple> userPostsDtoSimple = userPosts.map(postDtoMapper::toDtoSimple);

        return pageMapper.toPageResponse(userPostsDtoSimple);
    }

    @Override
    @Transactional
    public PageResponse<PostDtoSimple> showAllRecentPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Post> postPage = postRepository.findAll(pageable);
        Page<PostDtoSimple> postDtoSimplePage = postPage.map(postDtoMapper::toDtoSimple);

        return pageMapper.toPageResponse(postDtoSimplePage);
    }

    //ADMIN USER

    @Override
    @PreAuthorize("hasRole('ADMIN') and @userSecurityService.isActive(authentication)")
    @Transactional
    public void deletePostFromUser(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                PostNotFoundException::new
        );

        postRepository.delete(post);
    }

}
