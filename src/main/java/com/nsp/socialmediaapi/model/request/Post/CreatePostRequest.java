package com.nsp.socialmediaapi.model.request.Post;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePostRequest {
    @NotBlank(message = "title cannot be blank")
    private String title;

    @NotBlank(message = "must write something")
    private String content;
}
