package com.nsp.socialmediaapi.model.dto.Login;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "username required")
    private String username;

    @NotBlank(message = "password required")
    private String userPassword;
}
