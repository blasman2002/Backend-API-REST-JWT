package com.nsp.socialmediaapi.service;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.LikeAlreadyExistsException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.LikeNotFoundException;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.entity.Like;
import com.nsp.socialmediaapi.model.entity.LikeId;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.LikeRepository;
import com.nsp.socialmediaapi.service.Like.LikeServiceImpl;
import com.nsp.socialmediaapi.service.Post.PostService;
import com.nsp.socialmediaapi.service.User.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
public class LikeServiceImplTest {

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeServiceImpl likeService;

    public User PREPARED_USER = User.builder()
            .id(1L)
            .username("username")
            .userPassword("password")
            .role(Role.USER)
            .build();

    private void setAuthenticatedUser(User u) {
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn(u.getUsername());

        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void giveLikeToPost_shouldSuccessfullyGiveLikeToPost() {
        setAuthenticatedUser(PREPARED_USER);

        when(userService.findByUsername(PREPARED_USER.getUsername())).thenReturn(PREPARED_USER);

        Post post = Post.builder()
                .id(1L)
                .build();

        when(postService.findById(post.getId())).thenReturn(post);

        LikeId likeId = new LikeId();
        likeId.setPostId(post.getId());
        likeId.setUserId(PREPARED_USER.getId());

        when(likeRepository.existsById(likeId)).thenReturn(false);

        Like like = new Like();
        like.setId(likeId);
        like.setUser(PREPARED_USER);
        like.setPost(post);

        likeService.giveLikeToPost(1L);

        ArgumentCaptor<Like> likeArgumentCaptor = ArgumentCaptor.forClass(Like.class);

        verify(userService).findByUsername(PREPARED_USER.getUsername());
        verify(postService).findById(post.getId());
        verify(likeRepository).existsById(likeId);
        verify(likeRepository).save(likeArgumentCaptor.capture());

        Like savedLike = likeArgumentCaptor.getValue();

        assertEquals(PREPARED_USER, savedLike.getUser());
        assertEquals(post, savedLike.getPost());
        assertEquals(likeId, savedLike.getId());
    }

    @Test
    void giveLikeToPost_shouldThrowException_whenLikeAlreadyExists() {
        setAuthenticatedUser(PREPARED_USER);

        when(userService.findByUsername(PREPARED_USER.getUsername())).thenReturn(PREPARED_USER);

        Post post = Post.builder()
                .id(1L)
                .build();

        when(postService.findById(post.getId())).thenReturn(post);

        LikeId likeId = new LikeId();
        likeId.setPostId(post.getId());
        likeId.setUserId(PREPARED_USER.getId());

        when(likeRepository.existsById(likeId)).thenReturn(true);

        assertThrows(
                LikeAlreadyExistsException.class,
                () -> likeService.giveLikeToPost(1L)
        );

        verify(likeRepository, never()).save(any(Like.class));
    }

    @Test
    void removeLikeFromPost_shouldSuccessfullyRemoveLikeFromPost() {
        setAuthenticatedUser(PREPARED_USER);

        when(userService.findByUsername(PREPARED_USER.getUsername())).thenReturn(PREPARED_USER);

        Post post = Post.builder()
                .id(1L)
                .build();

        LikeId likeId = LikeId.builder()
                .postId(post.getId())
                .userId(PREPARED_USER.getId())
                .build();

        Like like = Like.builder()
                .id(likeId)
                .post(post)
                .user(PREPARED_USER)
                .build();

        when(likeRepository.findById(likeId)).thenReturn(Optional.of(like));

        likeService.removeLikeFromPost(1L);

        verify(likeRepository).delete(like);
    }

    @Test
    void removeLikeFromPost_shouldThrowException_whenLikeDoesNotExist() {
        setAuthenticatedUser(PREPARED_USER);

        when(userService.findByUsername(PREPARED_USER.getUsername())).thenReturn(PREPARED_USER);

        Post post = Post.builder()
                .id(1L)
                .build();

        LikeId likeId = LikeId.builder()
                .postId(post.getId())
                .userId(PREPARED_USER.getId())
                .build();

        Like like = Like.builder()
                .id(likeId)
                .post(post)
                .user(PREPARED_USER)
                .build();

        when(likeRepository.findById(likeId)).thenReturn(Optional.empty());

        assertThrows(
                LikeNotFoundException.class,
                () -> likeService.removeLikeFromPost(1L)
        );

        verify(likeRepository, never()).delete(like);
    }

}
