package com.nsp.socialmediaapi.mapper.User;

import com.nsp.socialmediaapi.model.dto.User.UserDto;
import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.model.entity.User;
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
                .role(u.getRole())
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

    public UserDisplayDto toUserDisplay(User u) {
        if (u == null) {
            return null;
        }

        return UserDisplayDto.builder()
                .id(u.getId())
                .username(u.getUsername())
                .build();

    }

}
