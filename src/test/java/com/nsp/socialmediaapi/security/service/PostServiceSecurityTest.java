package com.nsp.socialmediaapi.security.service;

import com.nsp.socialmediaapi.exception.NotFoundExceptions.PostNotFoundException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.model.request.Post.CreatePostRequest;
import com.nsp.socialmediaapi.service.Post.PostService;
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
public class PostServiceSecurityTest {

    @Autowired
    private PostService postService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserSecurityService userSecurityService;

    @Test
    @WithMockUser(username = "active_user")
    void createPost_shouldSuccessfullyAccess_whenUserIsActive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(true);

        when(userService.findByUsername("active_user")).thenThrow(new UserNotFoundException());

        assertThrows(
                UserNotFoundException.class,
                () -> postService.createPost(
                        CreatePostRequest.builder()
                                .title("title")
                                .content("content")
                                .build()
                )
        );
    }

    @Test
    @WithMockUser(username = "inactive_user")
    void createPost_shouldThrowException_whenUserIsInactive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(false);

        assertThrows(
                AuthorizationDeniedException.class,
                () -> postService.createPost(
                        CreatePostRequest.builder()
                                .title("title")
                                .content("content")
                                .build()
                )
        );
    }

    @Test
    @WithMockUser(username = "admin_user", roles = "ADMIN")
    void deletePostFromUser_shouldSuccessfullyAccess_whenUserIsAdminAndActive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(true);

        assertThrows(
                PostNotFoundException.class,
                () -> postService.deletePostFromUser(1L)
        );
    }

    @Test
    @WithMockUser(username = "admin_inactive", roles = "ADMIN")
    void deletePostFromUser_shouldThrowException_whenUserIsAdminAndNotActive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(false);

        assertThrows(
                AuthorizationDeniedException.class,
                () -> postService.deletePostFromUser(1L)
        );
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void deletePostFromUser_shouldThrowException_whenUserIsNotAdmin() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(true);

        assertThrows(
                AuthorizationDeniedException.class,
                () -> postService.deletePostFromUser(1L)
        );
    }

}
