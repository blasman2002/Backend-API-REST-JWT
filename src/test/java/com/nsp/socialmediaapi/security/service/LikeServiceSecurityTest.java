package com.nsp.socialmediaapi.security.service;

import com.nsp.socialmediaapi.exception.NotFoundExceptions.PostNotFoundException;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.service.Like.LikeService;
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
public class LikeServiceSecurityTest {

    @Autowired
    private LikeService likeService;

    @MockitoBean
    private PostService postService;

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
    public void giveLikeToPost_shouldSuccessfullyAccess_whenUserIsActive(){
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(true);

        when(userService.findByUsername("active_user")).thenReturn(USER_PREPARED);

        when(postService.findById(1L)).thenThrow(new PostNotFoundException());

        assertThrows(
                PostNotFoundException.class,
                () -> likeService.giveLikeToPost(1L)
        );
    }

    @Test
    @WithMockUser(username = "inactive_user")
    public void giveLikeToPost_shouldThrowException_whenUserIsInactive() {
        when(userSecurityService.isActive(any(Authentication.class))).thenReturn(false);

        assertThrows(
                AuthorizationDeniedException.class,
                () -> likeService.giveLikeToPost(1L)
        );
    }

}
