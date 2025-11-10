package com.nsp.socialmediaapi.model.dto.User;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String userPassword;
    private String userDesc;
    private LocalDateTime createdAt;
}
