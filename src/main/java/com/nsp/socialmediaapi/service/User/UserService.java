package com.nsp.socialmediaapi.service.User;

import com.nsp.socialmediaapi.model.entity.User;

public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    void deleteUserSelf();
    void banUser(Long id);
}
