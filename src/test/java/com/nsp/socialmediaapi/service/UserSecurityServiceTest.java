package com.nsp.socialmediaapi.service;

import com.nsp.socialmediaapi.model.enm.Status;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.service.User.UserSecurityService;
import com.nsp.socialmediaapi.service.User.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserSecurityServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserSecurityService userSecurityService;

    @Test
    void isActive_shouldReturnTrue_whenUserIsActive() {
        User user = User.builder()
                .username("username2000")
                .status(Status.ACTIVE)
                .build();

        when(authentication.getName()).thenReturn("username2000");
        when(userService.findByUsername("username2000")).thenReturn(user);

        boolean result = userSecurityService.isActive(authentication);

        assertTrue(result);

        verify(authentication).getName();
        verify(userService).findByUsername("username2000");
    }

    @Test
    void isActive_shouldReturnFalse_whenUserIsBanned() {
        User user = User.builder()
                .username("username2000")
                .status(Status.BANNED)
                .build();

        when(authentication.getName()).thenReturn("username2000");
        when(userService.findByUsername("username2000")).thenReturn(user);

        boolean result = userSecurityService.isActive(authentication);

        assertFalse(result);

        verify(authentication).getName();
        verify(userService).findByUsername("username2000");
    }

}
