package com.nsp.redsocial.mapper.User;

import com.nsp.redsocial.model.dto.User.UserDto;
import com.nsp.redsocial.model.dto.User.UserDtoSimple;
import com.nsp.redsocial.model.dto.User.UserProfileDto;
import com.nsp.redsocial.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserDto toDto(User u) {
        if (u == null) {
            return null;
        }

        return UserDto.builder()
                .id(u.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .userPassword(u.getUserPassword())
                .userDesc(u.getUserDesc())
                .createdAt(u.getCreatedAt())
                .build();
    }

    public UserProfileDto toProfileDto(User u) {
        if (u == null) {
            return null;
        }

        return UserProfileDto.builder()
                .id(u.getId())
                .username(u.getUsername())
                .userDesc(u.getUserDesc())
                .build();

    }

    public UserDtoSimple toDtoSimple(User u) {
        if (u == null) {
            return null;
        }

        return UserDtoSimple.builder()
                .id(u.getId())
                .username(u.getUsername())
                .build();

    }

}
