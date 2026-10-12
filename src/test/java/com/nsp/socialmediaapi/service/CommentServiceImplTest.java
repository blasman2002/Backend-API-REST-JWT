package com.nsp.socialmediaapi.service;

import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.CommentDoesNotBelongToPostException;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.CommentDoesNotBelongToUserException;
import com.nsp.socialmediaapi.mapper.Comment.CommentDtoMapper;
import com.nsp.socialmediaapi.mapper.Page.PageMapper;
import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.dto.Comment.CommentPostRequest;
import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.entity.Comment;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.CommentRepository;
import com.nsp.socialmediaapi.service.Comment.CommentServiceImpl;
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
public class CommentServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private PostService postService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentDtoMapper commentDtoMapper;

    @Mock
    private PageMapper pageMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    public User USER_PREPARED = User.builder()
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
    void commentPost_shouldSuccessfullyCreateCommentAndReturnDto() {
        setAuthenticatedUser(USER_PREPARED);

        when(userService.findByUsername(USER_PREPARED.getUsername())).thenReturn(USER_PREPARED);

        Post post = Post.builder()
                .id(1L)
                .title("tittle")
                .content("content")
                .build();

        CommentPostRequest request = CommentPostRequest.builder()
                .content("content")
                .build();

        Comment comment = Comment.builder()
                .id(1L)
                .post(post)
                .user(USER_PREPARED)
                .content(request.getContent())
                .build();

        UserDisplayDto userDto = UserDisplayDto.builder()
                .id(USER_PREPARED.getId())
                .username(USER_PREPARED.getUsername())
                .build();

        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(userDto)
                .build();

        when(postService.findById(post.getId())).thenReturn(post);
        when(commentDtoMapper.toDto(any(Comment.class))).thenReturn(commentDto);

        CommentDto result = commentService.commentPost(post.getId(), request);

        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);

        verify(postService).findById(post.getId());
        verify(commentRepository).save(commentArgumentCaptor.capture());
        verify(commentDtoMapper).toDto(commentArgumentCaptor.getValue());

        Comment savedComment = commentArgumentCaptor.getValue();

        assertEquals(post, savedComment.getPost());
        assertEquals(USER_PREPARED, savedComment.getUser());
        assertEquals(request.getContent(), savedComment.getContent());

        assertEquals(commentDto.getId(), result.getId());
        assertEquals(commentDto.getContent(), result.getContent());
        assertEquals(commentDto.getUser(), result.getUser());
    }

    @Test
    void deleteCommentFromPost_shouldSuccessfullyDeleteComment() {
        setAuthenticatedUser(USER_PREPARED);

        Post post = Post.builder()
                .id(1L)
                .title("tittle")
                .content("content")
                .build();

        Comment comment = Comment.builder()
                .id(1L)
                .post(post)
                .user(USER_PREPARED)
                .content("content")
                .build();

        when(postService.findById(post.getId())).thenReturn(post);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        commentService.deleteCommentFromPost(post.getId(), comment.getId());

        verify(commentRepository).delete(comment);
    }

    @Test
    void deleteCommentFromPost_shouldThrowException_whenCommentDoesNotBelongToPost() {
        setAuthenticatedUser(USER_PREPARED);

        Post incorrectPost = Post.builder()
                .id(2L)
                .title("tittle")
                .content("content")
                .build();

        Post post = Post.builder()
                .id(1L)
                .title("tittle")
                .content("content")
                .build();

        Comment comment = Comment.builder()
                .id(1L)
                .post(post)
                .user(USER_PREPARED)
                .content("content")
                .build();

        when(postService.findById(incorrectPost.getId())).thenReturn(incorrectPost);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        assertThrows(
                CommentDoesNotBelongToPostException.class,
                () -> commentService.deleteCommentFromPost(incorrectPost.getId(), comment.getId())
        );

        verify(commentRepository, never()).delete(comment);
    }

    @Test
    void deleteCommentFromPost_shouldThrowException_whenCommentDoesNotBelongToUser() {
        setAuthenticatedUser(USER_PREPARED);

        Post post = Post.builder()
                .id(1L)
                .title("tittle")
                .content("content")
                .build();

        User anotherUser = User.builder()
                .id(1L)
                .username("another")
                .role(Role.USER)
                .build();

        Comment comment = Comment.builder()
                .id(1L)
                .post(post)
                .user(anotherUser)
                .content("content")
                .build();

        when(postService.findById(post.getId())).thenReturn(post);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        assertThrows(
                CommentDoesNotBelongToUserException.class,
                () -> commentService.deleteCommentFromPost(post.getId(), comment.getId())
        );

        verify(commentRepository, never()).delete(comment);
    }

}
