package com.nsp.socialmediaapi.mapper.Star;

import com.nsp.socialmediaapi.mapper.User.UserDtoMapper;
import com.nsp.socialmediaapi.model.dto.Star.StarReceivedDto;
import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import com.nsp.socialmediaapi.model.entity.Star;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StarDtoMapper {

    private final UserDtoMapper userDtoMapper;

    public StarReceivedDto toStarReceiverDto(Star star) {
        if (star == null) {
            return null;
        }

        UserDisplayDto userGiverDisplay = userDtoMapper.toUserDisplay(star.getUser2());

        return StarReceivedDto.builder()
                .userGiver(userGiverDisplay)
                .createdAt(star.getCreatedAt())
                .build();
    }

}
