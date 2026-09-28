package com.nsp.socialmediaapi.service;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.UserAlreadyBannedException;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.SelfBanNotAllowedException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.enm.Status;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import com.nsp.socialmediaapi.service.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImplSecurityTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    public User ADMIN_PREPARED = User.builder()
            .id(1L)
            .username("username")
            .userPassword("password")
            .role(Role.ADMIN)
            .status(Status.ACTIVE)
            .build();

    public User USER_PREPARED = User.builder()
            .id(2L)
            .username("username2000")
            .userPassword("pass5000")
            .userDesc("bla bla bla")
            .role(Role.USER)
            .status(Status.ACTIVE)
            .build();

    @Test
    @WithMockUser(username = "username", roles = "ADMIN")
    void banUser_shouldSuccessfullyBanUser_ifLoggedUserIsAdminAndIsActive() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(ADMIN_PREPARED));

        when(userRepository.findById(2L)).thenReturn(Optional.of(USER_PREPARED));

        userService.banUser(2L);

        assertEquals(Status.BANNED, USER_PREPARED.getStatus());
    }

    @Test
    @WithMockUser(username = "username", roles = "USER")
    void banUser_shouldThrowException_ifLoggedUserIsNotAdmin() {

        assertThrows(
                AuthorizationDeniedException.class,
                () -> userService.banUser(2L)
        );

        verify(userRepository, never()).findById(2L);

    }

    @Test
    @WithMockUser(username = "username", roles = "ADMIN")
    void banUser_shouldThrowException_ifLoggedUserIsNotActive() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(ADMIN_PREPARED));

        ADMIN_PREPARED.setStatus(Status.DISABLED);

        when(userRepository.findById(2L)).thenReturn(Optional.of(USER_PREPARED));

        assertThrows(
                AuthorizationDeniedException.class,
                () -> userService.banUser(2L)
        );

        verify(userRepository, never()).findById(2L);

    }

    @Test
    @WithMockUser(username = "username", roles = "ADMIN")
    void banUser_shouldThrowException_ifUserNotFound() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(ADMIN_PREPARED));

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.banUser(2L)
        );

    }

    @Test
    @WithMockUser(username = "username", roles = "ADMIN")
    void banUser_shouldThrowException_ifUserAlreadyBanned() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(ADMIN_PREPARED));

        when(userRepository.findById(2L)).thenReturn(Optional.of(USER_PREPARED));

        USER_PREPARED.setStatus(Status.BANNED);

        assertThrows(
                UserAlreadyBannedException.class,
                () -> userService.banUser(2L)
        );

    }

    @Test
    @WithMockUser(username = "username", roles = "ADMIN")
    void banUser_shouldThrowException_ifUserIsSameAsLoggedUser() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(ADMIN_PREPARED));

        when(userRepository.findById(1L)).thenReturn(Optional.of(ADMIN_PREPARED));

        assertThrows(
                SelfBanNotAllowedException.class,
                () -> userService.banUser(1L)
        );
    }

}
