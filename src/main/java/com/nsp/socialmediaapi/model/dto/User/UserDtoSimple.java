package com.nsp.socialmediaapi.model.dto.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDtoSimple {
    private Long id;
    private String username;
}
