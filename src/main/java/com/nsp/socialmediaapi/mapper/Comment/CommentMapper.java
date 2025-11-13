package com.nsp.socialmediaapi.mapper.Comment;

import com.nsp.socialmediaapi.model.dto.Comment.CommentPostRequest;
import com.nsp.socialmediaapi.model.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment toComment(CommentPostRequest commentPostRequest) {
        if (commentPostRequest == null) {
            return null;
        }

        return Comment.builder()
                .content(commentPostRequest.getContent())
                .build();

    }
}
