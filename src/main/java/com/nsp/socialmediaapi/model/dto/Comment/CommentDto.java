package com.nsp.socialmediaapi.model.dto.Comment;

import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentDto {
    private Long id;
    private UserDisplayDto user;
    private String content;
    private LocalDateTime createdAt;
}
