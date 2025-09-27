package com.nsp.redsocial.model.dto.User;

import lombok.*;

@Getter
@Builder
public class UserNewDto {
    private String username;
    private String email;
    private String userPassword;
}
