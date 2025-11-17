package com.nsp.socialmediaapi.model.dto.Comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentPostRequest {
    @NotBlank(message = "must write something")
    private String content;
}
