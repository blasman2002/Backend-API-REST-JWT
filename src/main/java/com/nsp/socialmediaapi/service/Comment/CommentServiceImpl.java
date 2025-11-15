package com.nsp.socialmediaapi.service.Comment;

import com.nsp.socialmediaapi.exception.ResourceNotFoundException;
import com.nsp.socialmediaapi.mapper.Comment.CommentDtoMapper;
import com.nsp.socialmediaapi.mapper.Comment.CommentMapper;
import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.dto.Comment.CommentPostRequest;
import com.nsp.socialmediaapi.model.entity.Comment;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.CommentRepository;
import com.nsp.socialmediaapi.model.repository.PostRepository;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final CommentDtoMapper commentDtoMapper;

    @Override
    @Transactional
    public CommentDto commentPost(Long postId, CommentPostRequest commentPostRequest) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found")
        );

        User user = userRepository.findById(commentPostRequest.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );

        Comment comment = commentMapper.toEntity(commentPostRequest);
        comment.setPost(post);
        comment.setUser(user);

        Comment saved = commentRepository.save(comment);

        return commentDtoMapper.toDto(saved);
    }
}