package com.nsp.socialmediaapi.service.Comment;

import com.nsp.socialmediaapi.model.dto.Comment.CommentPostRequest;
import com.nsp.socialmediaapi.model.entity.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
    Comment commentPost(Long postId, CommentPostRequest request);
    void deleteCommentFromPost(Long postId, Long commentId);
    Page<Comment> getAllCommentsFromPost(Long postId, int page, int size);
}
