package com.nsp.redsocial.model.dto.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
public class UserNewDto {
    @NotNull(message = "username cannot be null")
    @NotBlank(message = "username cannot be blank")
    private String username;

    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    private String email;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    private String userPassword;
}
