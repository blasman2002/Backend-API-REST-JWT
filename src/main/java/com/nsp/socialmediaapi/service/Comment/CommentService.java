package com.nsp.socialmediaapi.service.Comment;

import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.dto.Comment.CommentNewDto;

public interface CommentService {
    CommentDto commentPost(Long postId, CommentNewDto commentNewDto);
}
