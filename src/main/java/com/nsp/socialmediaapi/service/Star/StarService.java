package com.nsp.socialmediaapi.service.Star;

import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Star.StarReceivedDto;

public interface StarService {
    void giveStar(Long userId);
    void removeStar(Long userId);
    PageResponse<StarReceivedDto> getStarsReceivedFromUser(Long userId, int page, int size);
}
