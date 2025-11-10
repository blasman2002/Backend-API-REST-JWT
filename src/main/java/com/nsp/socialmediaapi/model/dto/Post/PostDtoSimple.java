package com.nsp.socialmediaapi.model.dto.Post;

import com.nsp.socialmediaapi.model.dto.User.UserDtoSimple;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDtoSimple {
    private Long id;
    private UserDtoSimple userDtoSimple;
    private String title;
}
