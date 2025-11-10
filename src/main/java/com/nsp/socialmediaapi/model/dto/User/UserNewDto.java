package com.nsp.socialmediaapi.model.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
public class UserNewDto {
    @NotBlank(message = "username cannot be blank")
    @Size(min = 6, message = "username must have 6 or more characters")
    @Pattern(regexp = "^\\S*$", message = "username must not contain spaces")
    private String username;

    @NotBlank(message = "email cannot be blank")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "must enter a valid email")
    private String email;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 6, message = "password must have 6 or more characters")
    @Pattern(regexp = "^\\S*$", message = "password must not contain spaces")
    private String userPassword;
}
