package com.nsp.redsocial.model.dto.Post;

import com.nsp.redsocial.model.dto.User.UserDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDtoSimple {
    private Long id;
    private UserDto userDto;
    private String title;
}
