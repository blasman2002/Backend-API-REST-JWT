package com.nsp.socialmediaapi.service;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.UserAlreadyBannedException;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.SelfBanNotAllowedException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.mapper.User.UserDtoMapper;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.enm.Status;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import com.nsp.socialmediaapi.service.User.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserServiceImpl userService;

    public User USER_PREPARED = User.builder()
            .id(1L)
            .username("username2000")
            .role(Role.USER)
            .status(Status.ACTIVE)
            .build();

    public User BANNED_USER_PREPARED = User.builder()
            .id(3L)
            .username("username_banned")
            .role(Role.USER)
            .status(Status.BANNED)
            .build();

    public User ADMIN_PREPARED = User.builder()
            .id(2L)
            .username("admin2000")
            .role(Role.ADMIN)
            .status(Status.ACTIVE)
            .build();

    public UserProfileDto USER_PROFILE_PREPARED = UserProfileDto.builder()
            .id(1L)
            .username("username2000")
            .userDesc("bla bla bla")
            .starsCount(32L)
            .build();

    private void setAuthenticatedUser(User u) {
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn(u.getUsername());

        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void findById_shouldReturnPreparedUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER_PREPARED));

        User userDB = userService.findById(1L);

        assertEquals(1, userDB.getId());
    }

    @Test
    void findById_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.findById(1L)
        );

        verify(userRepository).findById(1L);
    }

    @Test
    void findUserProfile_shouldReturnUserProfileDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER_PREPARED));

        when(userDtoMapper.toProfileDto(USER_PREPARED)).thenReturn(USER_PROFILE_PREPARED);

        UserProfileDto userProfileDto = userService.findUserProfile(1L);

        verify(userDtoMapper).toProfileDto(USER_PREPARED);

        assertEquals(USER_PROFILE_PREPARED, userProfileDto);
    }

    @Test
    void findUserProfile_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.findById(1L)
        );

        verify(userRepository).findById(1L);
    }

    @Test
    void deleteUserSelf_shouldDeleteUser() {
        setAuthenticatedUser(USER_PREPARED);

        when(userRepository.findByUsername(USER_PREPARED.getUsername())).thenReturn(Optional.of(USER_PREPARED));

        userService.deleteUserSelf();

        verify(userRepository).delete(USER_PREPARED);
    }

    @Test
    void deleteUserSelf_shouldThrowException_whenUserNotFound() {
        setAuthenticatedUser(USER_PREPARED);

        when(userRepository.findByUsername(USER_PREPARED.getUsername())).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.deleteUserSelf()
        );

        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void banUser_shouldSuccessfullyBanUser() {
        setAuthenticatedUser(ADMIN_PREPARED);

        when(userRepository.findById(1L)).thenReturn(Optional.of(USER_PREPARED));

        userService.banUser(1L);

        assertEquals(Status.BANNED, USER_PREPARED.getStatus());
    }

    @Test
    void banUser_shouldThrowException_ifUserNotFound() {
        setAuthenticatedUser(ADMIN_PREPARED);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.banUser(1L)
        );

    }

    @Test
    void banUser_shouldThrowException_ifUserAlreadyBanned() {
        setAuthenticatedUser(ADMIN_PREPARED);

        when(userRepository.findById(1L)).thenReturn(Optional.of(BANNED_USER_PREPARED));

        assertThrows(
                UserAlreadyBannedException.class,
                () -> userService.banUser(1L)
        );
    }

    @Test
    void banUser_shouldThrowException_ifUserIsSameAsLoggedUser() {
        setAuthenticatedUser(ADMIN_PREPARED);

        when(userRepository.findById(2L)).thenReturn(Optional.of(ADMIN_PREPARED));

        assertThrows(
                SelfBanNotAllowedException.class,
                () -> userService.banUser(2L)
        );
    }

}
