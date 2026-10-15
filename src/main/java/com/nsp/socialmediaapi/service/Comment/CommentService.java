package com.nsp.socialmediaapi.service.Comment;

import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.request.Comment.CommentPostRequest;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.entity.Comment;

public interface CommentService {
    Comment findById(Long id);
    CommentDto commentPost(Long postId, CommentPostRequest request);
    void deleteCommentFromPost(Long postId, Long commentId);
    PageResponse<CommentDto> getAllCommentsFromPost(Long postId, int page, int size);
    void deleteUserCommentFromPost(Long commentId);
}
