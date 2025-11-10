package com.nsp.socialmediaapi.mapper.User;

import com.nsp.socialmediaapi.model.dto.User.UserNewDto;
import com.nsp.socialmediaapi.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toNewUser(UserNewDto udto) {
        if (udto == null) {
            return null;
        }

        return User.builder()
                .username(udto.getUsername())
                .email(udto.getEmail())
                .userPassword(udto.getUserPassword())
                .build();
    }

}
