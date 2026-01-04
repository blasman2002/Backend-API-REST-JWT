package com.nsp.socialmediaapi.service.User;

import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;

public interface UserService {
    UserProfileDto findProfile(Long id);
    void deleteUser();
}
