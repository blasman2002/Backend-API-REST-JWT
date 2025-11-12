package com.nsp.socialmediaapi.model.dto.Comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentNewDto {
    private Long userId;

    @NotBlank(message = "must write something")
    private String content;
}
