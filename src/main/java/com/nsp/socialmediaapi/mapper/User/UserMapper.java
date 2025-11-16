package com.nsp.socialmediaapi.mapper.User;

import com.nsp.socialmediaapi.model.dto.User.CreateUserRequest;
import com.nsp.socialmediaapi.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity(CreateUserRequest udto) {
        if (udto == null) {
            return null;
        }

        return User.builder()
                .username(udto.getUsername())
                .email(udto.getEmail())
                .userPassword(passwordEncoder.encode(udto.getUserPassword()))
                .build();
    }

}
