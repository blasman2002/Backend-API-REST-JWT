package com.nsp.socialmediaapi.model.dto.Comment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentNewDto {
    private Long userId;
    private String content;
}
