package com.nsp.socialmediaapi.model.dto.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDisplayDto {
    private Long id;
    private String username;
}
