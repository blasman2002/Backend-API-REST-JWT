package com.nsp.socialmediaapi.service.Comment;

import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.CommentDoesNotBelongToPostException;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.CommentDoesNotBelongToUserException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.*;
import com.nsp.socialmediaapi.mapper.Comment.CommentDtoMapper;
import com.nsp.socialmediaapi.mapper.Page.PageMapper;
import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.dto.Comment.CommentPostRequest;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.entity.Comment;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.CommentRepository;
import com.nsp.socialmediaapi.service.Post.PostService;
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
public class CommentServiceImpl implements CommentService {

    private final UserService userService;

    private final PostService postService;

    private final CommentRepository commentRepository;

    private final CommentDtoMapper commentDtoMapper;

    private final PageMapper pageMapper;

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                CommentNotFoundException::new
        );
    }

    @Override
    @PreAuthorize("@userSecurityService.isActive(authentication)")
    @Transactional
    public CommentDto commentPost(Long postId, CommentPostRequest request) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUsername(currentUsername);

        Post post = postService.findById(postId);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(request.getContent());

        commentRepository.save(comment);

        return commentDtoMapper.toDto(comment);
    }

    @Override
    @Transactional
    public void deleteCommentFromPost(Long postId, Long commentId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Post post = postService.findById(postId);

        Comment comment = findById(commentId);

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new CommentDoesNotBelongToPostException();
        }

        if (!comment.getUser().getUsername().equals(currentUsername)) {
            throw new CommentDoesNotBelongToUserException();
        }

        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public PageResponse<CommentDto> getAllCommentsFromPost(Long postId, int page, int size) {
        postService.validateExistsById(postId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Comment> commentsPage = commentRepository.findByPostId(postId, pageable);
        Page<CommentDto> commentDtoPage = commentsPage.map(commentDtoMapper::toDto);

        return pageMapper.toPageResponse(commentDtoPage);
    }

    //ADMIN USER

    @Override
    @PreAuthorize("hasRole('ADMIN') and @userSecurityService.isActive(authentication)")
    @Transactional
    public void deleteUserCommentFromPost(Long commentId) {
        Comment comment = findById(commentId);
        commentRepository.delete(comment);
    }

}