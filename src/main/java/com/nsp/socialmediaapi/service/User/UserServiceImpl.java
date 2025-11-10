package com.nsp.socialmediaapi.service.User;

import com.nsp.socialmediaapi.exception.ResourceNotFoundException;
import com.nsp.socialmediaapi.exception.UserAlreadyExistsException;
import com.nsp.socialmediaapi.mapper.User.UserDtoMapper;
import com.nsp.socialmediaapi.mapper.User.UserMapper;
import com.nsp.socialmediaapi.model.dto.User.UserDto;
import com.nsp.socialmediaapi.model.dto.User.UserNewDto;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.UserRepository;
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
        if (userRepository.existsByUsername(userNewDto.getUsername())) {
            throw new UserAlreadyExistsException("Username already in use");
        }

        if (userRepository.existsByEmail(userNewDto.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        User u = userMapper.toNewUser(userNewDto);
        User saved = userRepository.save(u);

        return userDtoMapper.toDto(saved);
    }

    @Override
    public UserProfileDto findProfile(Long id) {
        User u = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        return userDtoMapper.toProfileDto(u);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        userRepository.delete(user);
    }

}