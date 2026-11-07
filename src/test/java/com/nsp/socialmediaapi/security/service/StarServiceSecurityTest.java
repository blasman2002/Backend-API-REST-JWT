package com.nsp.socialmediaapi.security.service;

import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.service.Star.StarService;
import com.nsp.socialmediaapi.service.User.UserSecurityService;
import com.nsp.socialmediaapi.service.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class StarServiceSecurityTest {

    @Autowired
	private StarService starService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserSecurityService userSecurityService;

    @Test
    @WithMockUser(username = "active_user")
    void giveStar_shouldSuccessfullyAccess_whenLoggedUserIsActive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(true);

        when(userService.findByUsername("active_user")).thenThrow(new UserNotFoundException());

        assertThrows(
                UserNotFoundException.class,
                () -> starService.giveStar(1L)
        );
    }

    @Test
    @WithMockUser(username = "inactive_user")
    void giveStar_shouldThrowException_whenLoggedUserIsInactive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(false);

        assertThrows(
                AuthorizationDeniedException.class,
                () -> starService.giveStar(1L)
        );
    }

}
