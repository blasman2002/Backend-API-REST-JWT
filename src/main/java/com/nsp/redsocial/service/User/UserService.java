package com.nsp.redsocial.service.User;

import com.nsp.redsocial.model.dto.User.UserDto;
import com.nsp.redsocial.model.dto.User.UserNewDto;
import com.nsp.redsocial.model.dto.User.UserProfileDto;

public interface UserService {
    UserDto registerUser(UserNewDto userNewDto);
    UserDto findUserId(Long id);
    UserProfileDto findProfile(Long id);
    void deleteUser(Long id);
}
