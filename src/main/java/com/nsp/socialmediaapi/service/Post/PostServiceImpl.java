package com.nsp.socialmediaapi.service.Post;

import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.PostDoesNotBelongToUserException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.PostNotFoundException;
import com.nsp.socialmediaapi.model.dto.Post.CreatePostRequest;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.PostRepository;
import com.nsp.socialmediaapi.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(
                PostNotFoundException::new
        );
    }

    @Override
    @Transactional
    public Post createPost(CreatePostRequest request) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUsername(currentUsername);

        Post post = new Post();
        post.setUser(user);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
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
    public Page<Post> showUserPosts(Long id, int page, int size) {
        userService.findById(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findByUserId(id, pageable);
    }

    @Override
    @Transactional
    public Page<Post> showAllRecentPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findAll(pageable);
    }

}
