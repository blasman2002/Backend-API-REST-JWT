package com.nsp.socialmediaapi.model.dto.Post;

import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDto {
    private Long id;
    private UserDisplayDto userDisplayDto;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
