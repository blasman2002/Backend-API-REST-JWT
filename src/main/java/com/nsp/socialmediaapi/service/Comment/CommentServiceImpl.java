package com.nsp.socialmediaapi.service.Comment;

import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.CommentDoesNotBelongToPostException;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.CommentDoesNotBelongToUserException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.*;
import com.nsp.socialmediaapi.mapper.Comment.CommentDtoMapper;
import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.dto.Comment.CommentPostRequest;
import com.nsp.socialmediaapi.model.entity.Comment;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.CommentRepository;
import com.nsp.socialmediaapi.model.repository.PostRepository;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final CommentDtoMapper commentDtoMapper;

    @Override
    @Transactional
    public CommentDto commentPost(Long postId, CommentPostRequest request) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(currentUsername).orElseThrow(
                UserNotFoundException::new
        );

        Post post = postRepository.findById(postId).orElseThrow(
                PostNotFoundException::new
        );

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(post.getContent());

        Comment savedComment = commentRepository.save(comment);

        return commentDtoMapper.toDto(savedComment);
    }

    @Override
    @Transactional
    public void deleteCommentFromPost(Long postId, Long commentId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Post post = postRepository.findById(postId).orElseThrow(
                PostNotFoundException::new
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                CommentNotFoundException::new
        );

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new CommentDoesNotBelongToPostException();
        }

        if (!comment.getUser().getUsername().equals(currentUsername)) {
            throw new CommentDoesNotBelongToUserException();
        }

        commentRepository.delete(comment);
    }

}