package com.nsp.socialmediaapi.model.dto.Star;

import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class StarReceivedDto {
    UserDisplayDto userGiver;
    LocalDateTime createdAt;
}
