package com.nsp.redsocial.model.dto.Comment;

import com.nsp.redsocial.model.dto.User.UserDtoSimple;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentDto {
    private Long id;
    private UserDtoSimple user;
    private String content;
    private LocalDateTime createdAt;
}
