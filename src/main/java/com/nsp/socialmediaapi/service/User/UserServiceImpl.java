package com.nsp.socialmediaapi.service.User;

import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.mapper.User.UserDtoMapper;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserDtoMapper userDtoMapper;

    @Override
    public UserProfileDto findProfile(Long id) {
        User u = userRepository.findById(id).orElseThrow(
                UserNotFoundException::new
        );

        return userDtoMapper.toProfileDto(u);
    }

    @Override
    @Transactional
    public void deleteUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(currentUsername).orElseThrow(
                UserNotFoundException::new
        );

        userRepository.delete(user);
    }

}