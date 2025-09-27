package com.nsp.redsocial.model.dto.Post;

import com.nsp.redsocial.model.dto.User.UserDtoSimple;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDto {
    private Long id;
    private UserDtoSimple userDtoSimple;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
