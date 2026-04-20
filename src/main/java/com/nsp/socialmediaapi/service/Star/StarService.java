package com.nsp.socialmediaapi.service.Star;

import com.nsp.socialmediaapi.model.entity.Star;
import org.springframework.data.domain.Page;

public interface StarService {
    void giveStar(Long userId);
    void removeStar(Long userId);
    Page<Star> getStarsReceivedFromUser(Long userId, int page, int size);
}
