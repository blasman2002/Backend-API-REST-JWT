package com.nsp.socialmediaapi.service.Star;

public interface StarService {
    void giveStar(Long userIdGiver, Long userIdReceiver);
    void removeStar(Long userIdGiver, Long userIdReceiver);
}
