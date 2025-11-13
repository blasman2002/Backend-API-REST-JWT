package com.nsp.socialmediaapi.service.Comment;

import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.dto.Comment.CommentPostRequest;

public interface CommentService {
    CommentDto commentPost(Long postId, CommentPostRequest commentPostRequest);
}
