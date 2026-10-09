package com.nsp.socialmediaapi.service;

import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.PostDoesNotBelongToUserException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.PostNotFoundException;
import com.nsp.socialmediaapi.mapper.Page.PageMapper;
import com.nsp.socialmediaapi.mapper.Post.PostDtoMapper;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Post.CreatePostRequest;
import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.PostRepository;
import com.nsp.socialmediaapi.service.Post.PostServiceImpl;
import com.nsp.socialmediaapi.service.User.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @Mock
    private PostDtoMapper postDtoMapper;

    @Mock
    private PageMapper pageMapper;

    @InjectMocks
    private PostServiceImpl postService;

    public User PREPARED_USER = User.builder()
            .id(1L)
            .username("user")
            .userPassword("pass")
            .role(Role.USER)
            .build();

    public Post PREPARED_POST = Post.builder()
            .id(1L)
            .user(PREPARED_USER)
            .title("tittle")
            .content("content")
            .build();

    public CreatePostRequest PREPARED_CREATE_REQUEST = CreatePostRequest.builder()
            .title("tittle")
            .content("content")
            .build();

    private void setAuthenticatedUser(User u) {
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn(u.getUsername());

        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void findById_shouldReturnPreparedPost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(PREPARED_POST));

        Post postDB = postService.findById(1L);

        assertEquals(1,  postDB.getId());
    }

    @Test
    void findById_shouldThrowException_whenPostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                PostNotFoundException.class,
                () -> postService.findById(1L)
        );
    }

    @Test
    void createPost_shouldSuccessfullyCreatePostAndReturnPostDto() {
        setAuthenticatedUser(PREPARED_USER);

        when(userService.findByUsername(PREPARED_USER.getUsername())).thenReturn(PREPARED_USER);

        PostDto dto = PostDto.builder()
                .id(PREPARED_POST.getId())
                .title(PREPARED_POST.getTitle())
                .content(PREPARED_POST.getContent())
                .build();

        when(postDtoMapper.toDto(any(Post.class))).thenReturn(dto);

        PostDto result = postService.createPost(PREPARED_CREATE_REQUEST);

        verify(userService).findByUsername(PREPARED_USER.getUsername());
        verify(postRepository).save(any(Post.class));
        verify(postDtoMapper).toDto(any(Post.class));

        assertEquals(PREPARED_POST.getId(), result.getId());
        assertEquals(PREPARED_POST.getTitle(), result.getTitle());
        assertEquals(PREPARED_POST.getContent(), result.getContent());
    }

    @Test
    void deleteOwnPost_shouldSuccessfullyDeleteUserPost() {
        setAuthenticatedUser(PREPARED_USER);

        when(userService.findByUsername(PREPARED_USER.getUsername())).thenReturn(PREPARED_USER);
        when(postRepository.findById(PREPARED_POST.getId())).thenReturn(Optional.of(PREPARED_POST));

        postService.deleteOwnPost(PREPARED_POST.getId());

        verify(postRepository).delete(PREPARED_POST);
    }

    @Test
    void deleteOwnPost_shouldThrowException_ifPostDoesNotBelongToLoggedUser() {
        setAuthenticatedUser(PREPARED_USER);

        User incorrectUser = User.builder()
                .id(2L)
                .username("incorrect_user")
                .build();

        PREPARED_POST.setUser(incorrectUser);

        when(userService.findByUsername(PREPARED_USER.getUsername())).thenReturn(PREPARED_USER);
        when(postRepository.findById(PREPARED_POST.getId())).thenReturn(Optional.of(PREPARED_POST));

        assertThrows(
                PostDoesNotBelongToUserException.class,
                () -> postService.deleteOwnPost(PREPARED_POST.getId())
        );

        verify(postRepository, never()).delete(any(Post.class));
    }

    //Note to myself: since I get warn with these mocks, I should investigate a better way to do this....
    // *
    // * :)
    // *

    @Test
    void showUserPosts_shouldSuccessfullyReturnPagedUserPosts() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("createdAt").descending());

        Post post = mock(Post.class);

        PostDtoSimple postDtoSimple = mock(PostDtoSimple.class);

        Page<Post> postPage = new PageImpl<>(List.of(post), pageable, 1);

        Page<PostDtoSimple> postDtoPage = new PageImpl<>(List.of(postDtoSimple), pageable, 1);

        PageResponse<PostDtoSimple> expectedResponse = mock(PageResponse.class);

        when(postRepository.findByUserId(PREPARED_USER.getId(), pageable)).thenReturn(postPage);
        when(postDtoMapper.toDtoSimple(post)).thenReturn(postDtoSimple);
        when(pageMapper.toPageResponse(postDtoPage)).thenReturn(expectedResponse);

        PageResponse<PostDtoSimple> result = postService.showUserPosts(PREPARED_USER.getId(), 1, 10);

        assertSame(expectedResponse, result);

        verify(postRepository).findByUserId(PREPARED_USER.getId(), pageable);
        verify(postDtoMapper).toDtoSimple(post);
        verify(pageMapper).toPageResponse(postDtoPage);
    }

    @Test
    void showAllRecentPosts_shouldSuccessfullyReturnPagedUserPosts() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("createdAt").descending());

        Post post = mock(Post.class);

        PostDtoSimple postDtoSimple = mock(PostDtoSimple.class);

        Page<Post> postPage = new PageImpl<>(List.of(post), pageable, 1);

        Page<PostDtoSimple> postDtoPage = new PageImpl<>(List.of(postDtoSimple), pageable, 1);

        PageResponse<PostDtoSimple> expectedResponse = mock(PageResponse.class);

        when(postRepository.findAll(pageable)).thenReturn(postPage);
        when(postDtoMapper.toDtoSimple(post)).thenReturn(postDtoSimple);
        when(pageMapper.toPageResponse(postDtoPage)).thenReturn(expectedResponse);

        PageResponse<PostDtoSimple> result = postService.showAllRecentPosts(1, 10);

        assertSame(expectedResponse, result);

        verify(postRepository).findAll(pageable);
        verify(postDtoMapper).toDtoSimple(post);
        verify(pageMapper).toPageResponse(postDtoPage);
    }

    @Test
    void deletePostFromUser_shouldSuccessfullyDeletePost() {
        when(postRepository.findById(PREPARED_POST.getId())).thenReturn(Optional.of(PREPARED_POST));

        postService.deletePostFromUser(PREPARED_POST.getId());

        verify(postRepository).delete(PREPARED_POST);
    }

    @Test
    void deletePostFromUser_shouldThrowException_whenPostNotFound() {
        when(postRepository.findById(PREPARED_POST.getId())).thenReturn(Optional.empty());

        assertThrows(
                PostNotFoundException.class,
                () -> postService.deletePostFromUser(PREPARED_POST.getId())
        );

        verify(postRepository, never()).delete(any(Post.class));
    }

}
