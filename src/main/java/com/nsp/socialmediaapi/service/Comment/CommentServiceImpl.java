package com.nsp.socialmediaapi.service.Comment;

import com.nsp.socialmediaapi.exception.customExceptions.ResourceNotFoundException;
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
                () -> new ResourceNotFoundException("authenticated user not found")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found")
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
                () -> new ResourceNotFoundException("post not found")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment not found")
        );

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new IllegalArgumentException("comment does not belong to this post");
        }

        if (!comment.getUser().getUsername().equals(currentUsername)) {
            throw new IllegalArgumentException("comment does not belong to this user");
        }

        commentRepository.delete(comment);
    }

}