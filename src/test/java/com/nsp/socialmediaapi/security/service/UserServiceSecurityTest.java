package com.nsp.socialmediaapi.security.service;

import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.model.request.User.UpdateUserProfileInfoRequest;
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
public class UserServiceSecurityTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserSecurityService userSecurityService;

    @Test
    @WithMockUser(username = "admin_user", roles = "ADMIN")
    void banUser_shouldSuccessfullyAccess_whenUserIsAdminAndActive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(true);

        assertThrows(
                UserNotFoundException.class,
                () -> userService.banUser(1L)
        );
    }

    @Test
    @WithMockUser(username = "admin_user_inactive", roles = "ADMIN")
    void banUser_shouldThrowException_whenUserIsInactive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(false);

        assertThrows(
                AuthorizationDeniedException.class,
                () -> userService.banUser(1L)
        );
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void banUser_shouldThrowException_whenUserIsNotAdmin() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(true);

        assertThrows(
                AuthorizationDeniedException.class,
                () -> userService.banUser(1L)
        );
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void updateUserInfo_shouldSuccessfullyAccess_whenUserIsActiveOrBanned() {
        when(userSecurityService.isActiveOrBanned(any(Authentication.class))).thenReturn(true);

        UpdateUserProfileInfoRequest request = UpdateUserProfileInfoRequest.builder()
                .newUserDesc("desc")
                .build();

        assertThrows(
                UserNotFoundException.class,
                () -> userService.updateUserInfo(request)
        );
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void updateUserInfo_shouldThrowException_whenUserIsInactive() {
        when(userSecurityService.isActiveOrBanned(any(Authentication.class))).thenReturn(false);

        UpdateUserProfileInfoRequest request = UpdateUserProfileInfoRequest.builder()
                .newUserDesc("desc")
                .build();

        assertThrows(
                AuthorizationDeniedException.class,
                () -> userService.updateUserInfo(request)
        );
    }

}
