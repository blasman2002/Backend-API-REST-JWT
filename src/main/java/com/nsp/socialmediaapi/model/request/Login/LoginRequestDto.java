package com.nsp.socialmediaapi.model.request.Login;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "username required")
    private String username;

    @NotBlank(message = "password required")
    private String userPassword;
}
