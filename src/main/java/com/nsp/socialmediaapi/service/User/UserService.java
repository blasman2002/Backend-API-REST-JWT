package com.nsp.socialmediaapi.service.User;

import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.model.entity.User;

public interface UserService {
    User findByUsername(String username);
    UserProfileDto findProfile(Long id);
    void deleteUser();
}
