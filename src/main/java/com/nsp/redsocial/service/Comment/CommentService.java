package com.nsp.redsocial.service.Comment;

import com.nsp.redsocial.model.dto.Comment.CommentDto;
import com.nsp.redsocial.model.dto.Comment.CommentNewDto;

public interface CommentService {
    CommentDto commentPost(Long postId, CommentNewDto commentNewDto);
}
