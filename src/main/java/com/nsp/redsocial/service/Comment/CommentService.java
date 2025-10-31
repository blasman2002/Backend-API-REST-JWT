package com.nsp.redsocial.service.Comment;

import com.nsp.redsocial.model.dto.Comment.CommentNewDto;
import com.nsp.redsocial.model.entity.Comment;

public interface CommentService {
    Comment commentPost(Long postId, CommentNewDto commentNewDto);

}
