package com.nsp.socialmediaapi.service.Comment;

import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.dto.Comment.CommentPostRequest;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;

public interface CommentService {
    CommentDto commentPost(Long postId, CommentPostRequest request);
    void deleteCommentFromPost(Long postId, Long commentId);
    PageResponse<CommentDto> getAllCommentsFromPost(Long postId, int page, int size);
    void deleteUserCommentFromPost(Long postId, Long commentId);
}
