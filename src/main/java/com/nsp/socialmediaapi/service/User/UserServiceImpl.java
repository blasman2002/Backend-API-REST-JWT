package com.nsp.socialmediaapi.service.User;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.UserAlreadyBannedException;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.SelfBanNotAllowedException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.mapper.User.UserDtoMapper;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.model.enm.Status;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserDtoMapper userDtoMapper;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                UserNotFoundException::new
        );
    }

    @Override
    public UserProfileDto findUserProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                UserNotFoundException::new
        );

        return userDtoMapper.toProfileDto(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                UserNotFoundException::new
        );
    }

    @Override
    @Transactional
    public void deleteUserSelf() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(currentUsername).orElseThrow(
                UserNotFoundException::new
        );

        userRepository.delete(user);
    }

    //ADMIN USER

    @Override
    @PreAuthorize("hasRole('ADMIN') and @userSecurityService.isActive(authentication)")
    @Transactional
    public void banUser(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findById(id).orElseThrow(
                UserNotFoundException::new
        );

        if (user.getStatus().equals(Status.BANNED)) {
            throw new UserAlreadyBannedException();
        }

        if (user.getUsername().equals(currentUsername)) {
            throw new SelfBanNotAllowedException();
        }

        user.setStatus(Status.BANNED);
    }

}