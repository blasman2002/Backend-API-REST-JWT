package com.nsp.socialmediaapi.service.User;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.UserAlreadyBannedException;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.SelfBanNotAllowedException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.mapper.UserMapper;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.model.enm.Status;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.StarRepository;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import com.nsp.socialmediaapi.model.request.User.UpdateUserProfileInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final StarRepository starRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                UserNotFoundException::new
        );
    }

    @Override
    public UserProfileDto findUserProfile(Long id) {
        User user = findById(id);

        Long starsCount = starRepository.countByUser2_Id(user.getId());

        return userMapper.mapToUserProfileDto(user, starsCount);
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

        User user = findByUsername(currentUsername);

        user.setStatus(Status.DISABLED);
    }

    @Override
    @PreAuthorize("@userSecurityService.isActiveOrBanned(authentication)")
    @Transactional
    public void updateUserInfo(UpdateUserProfileInfoRequest request) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = findByUsername(currentUsername);

        user.setUserDesc(request.getNewUserDesc());
    }

    //ADMIN USER

    @Override
    @PreAuthorize("hasRole('ADMIN') and @userSecurityService.isActive(authentication)")
    @Transactional
    public void banUser(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = findById(id);

        if (user.getStatus().equals(Status.BANNED)) {
            throw new UserAlreadyBannedException();
        }

        if (user.getUsername().equals(currentUsername)) {
            throw new SelfBanNotAllowedException();
        }

        user.setStatus(Status.BANNED);
    }

}