package com.nsp.socialmediaapi.mapper;

import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDisplayDto mapToUserDisplayDto(User user);

    @Mapping(target = "starsCount", source = "starsCount")
    UserProfileDto mapToUserProfileDto(User user, Long starsCount);

}
