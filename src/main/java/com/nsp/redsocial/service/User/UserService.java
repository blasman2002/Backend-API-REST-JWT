package com.nsp.redsocial.service.User;

import com.nsp.redsocial.model.dto.User.UserDto;
import com.nsp.redsocial.model.dto.User.UserNewDto;
import com.nsp.redsocial.model.dto.User.UserProfileDto;

public interface UserService {
    UserDto save(UserNewDto userNewDto);
    UserDto findById(Long id);
    UserProfileDto findByUsername(String username);
    void delete(Long id);
}
