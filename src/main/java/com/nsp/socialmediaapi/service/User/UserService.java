package com.nsp.socialmediaapi.service.User;

import com.nsp.socialmediaapi.model.dto.User.UserDto;
import com.nsp.socialmediaapi.model.dto.User.UserNewDto;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;

public interface UserService {
    UserDto registerUser(UserNewDto userNewDto);
    UserProfileDto findProfile(Long id);
    void deleteUser(Long id);
}
