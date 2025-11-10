package com.nsp.socialmediaapi.mapper.Comment;

import com.nsp.socialmediaapi.model.dto.Comment.CommentNewDto;
import com.nsp.socialmediaapi.model.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment toNewComment(CommentNewDto commentNewDto) {
        if (commentNewDto == null) {
            return null;
        }

        return Comment.builder()
                .content(commentNewDto.getContent())
                .build();

    }
}
