package com.nsp.redsocial.service.Comment;

import com.nsp.redsocial.mapper.Comment.CommentDtoMapper;
import com.nsp.redsocial.mapper.Comment.CommentMapper;
import com.nsp.redsocial.model.dto.Comment.CommentDto;
import com.nsp.redsocial.model.dto.Comment.CommentNewDto;
import com.nsp.redsocial.model.entity.Comment;
import com.nsp.redsocial.model.entity.Post;
import com.nsp.redsocial.model.entity.User;
import com.nsp.redsocial.model.repository.CommentRepository;
import com.nsp.redsocial.model.repository.PostRepository;
import com.nsp.redsocial.model.repository.UserRepository;
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
    public CommentDto commentPost(Long postId, CommentNewDto commentNewDto) {
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(commentNewDto.getUserId()).orElse(null);

        Comment comment = commentMapper.toNewComment(commentNewDto);
        comment.setPost(post);
        comment.setUser(user);

        Comment saved = commentRepository.save(comment);

        return commentDtoMapper.toDto(saved);
    }
}
