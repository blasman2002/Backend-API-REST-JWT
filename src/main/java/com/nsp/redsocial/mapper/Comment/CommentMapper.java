package com.nsp.redsocial.mapper.Comment;

import com.nsp.redsocial.model.dto.Comment.CommentNewDto;
import com.nsp.redsocial.model.entity.Comment;
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
