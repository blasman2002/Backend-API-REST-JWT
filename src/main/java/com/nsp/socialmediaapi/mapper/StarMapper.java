package com.nsp.socialmediaapi.mapper;

import com.nsp.socialmediaapi.model.dto.Star.StarReceivedDto;
import com.nsp.socialmediaapi.model.entity.Star;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface StarMapper {

    @Mapping(target = "userGiver", source = "user1")
    StarReceivedDto mapToStarReceivedDto(Star star);

}
