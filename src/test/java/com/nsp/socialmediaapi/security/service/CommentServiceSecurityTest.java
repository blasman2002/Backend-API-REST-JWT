package com.nsp.socialmediaapi.security.service;

import com.nsp.socialmediaapi.exception.NotFoundExceptions.CommentNotFoundException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.PostNotFoundException;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.request.Comment.CommentPostRequest;
import com.nsp.socialmediaapi.service.Comment.CommentService;
import com.nsp.socialmediaapi.service.User.UserSecurityService;
import com.nsp.socialmediaapi.service.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentServiceSecurityTest {

    @Autowired
    private CommentService commentService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserSecurityService userSecurityService;

    private final User USER_PREPARED = User.builder()
            .username("user")
            .id(1L)
            .build();

    @Test
    @WithMockUser(username = "active_user")
    public void commentPost_shouldSuccessfullyAccess_whenLoggedUserIsActive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(true);

        when(userService.findByUsername("active_user")).thenReturn(USER_PREPARED);

        CommentPostRequest request = CommentPostRequest.builder()
                .content("content")
                .build();

        assertThrows(
                PostNotFoundException.class,
                () -> commentService.commentPost(1L, request)
        );
    }

    @Test
    @WithMockUser(username = "banned_user")
    void commentPost_shouldThrowException_whenLoggedUserIsNotActive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(false);

        CommentPostRequest request = CommentPostRequest.builder()
                .content("content")
                .build();

        assertThrows(
                AuthorizationDeniedException.class,
                () -> commentService.commentPost(1L, request)
        );
    }

    @Test
    @WithMockUser(username = "admin_user", roles = "ADMIN")
    void deleteUserComment_shouldSuccessfullyAccess_whenLoggedUserIsAdminAndActive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(true);

        assertThrows(
                CommentNotFoundException.class,
                () -> commentService.deleteUserCommentFromPost(1L)
        );
    }

    @Test
    @WithMockUser(username = "admin_user_banned", roles = "ADMIN")
    void deleteUserCommentFromPost_shouldThrowException_whenLoggedUserIsNotActive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(false);

        assertThrows(
                AuthorizationDeniedException.class,
                () -> commentService.deleteUserCommentFromPost(1L)
        );
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void deleteUserCommentFromPost_shouldThrowException_whenLoggedUserNotAdmin() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(true);

        assertThrows(
                AuthorizationDeniedException.class,
                () -> commentService.deleteUserCommentFromPost(1L)
        );
    }

}
