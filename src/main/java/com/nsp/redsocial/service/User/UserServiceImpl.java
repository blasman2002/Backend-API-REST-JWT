package com.nsp.redsocial.service.User;

import com.nsp.redsocial.mapper.User.UserDtoMapper;
import com.nsp.redsocial.mapper.User.UserMapper;
import com.nsp.redsocial.model.dto.User.UserDto;
import com.nsp.redsocial.model.dto.User.UserNewDto;
import com.nsp.redsocial.model.dto.User.UserProfileDto;
import com.nsp.redsocial.model.entity.User;
import com.nsp.redsocial.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserDtoMapper userDtoMapper;

    @Override
    public UserDto registerUser(UserNewDto userNewDto) {
        User u = userMapper.toNewUser(userNewDto);
        User saved = userRepository.save(u);
        return userDtoMapper.toDto(saved);
    }

    @Override
    public UserDto findUserId(Long id) {
        return userDtoMapper.toDto(userRepository.findById(id).orElse(null));
    }

    public UserProfileDto findProfile(Long id) {
        User u = userRepository.findById(id).orElse(null);
        return userDtoMapper.toProfileDto(u);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}