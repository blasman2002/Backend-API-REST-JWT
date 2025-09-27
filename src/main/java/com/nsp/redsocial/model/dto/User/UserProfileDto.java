package com.nsp.redsocial.model.dto.User;

import lombok.*;

@Getter
@Builder
public class UserProfileDto {
    private Long id;
    private String username;
    private String userDesc;
}
