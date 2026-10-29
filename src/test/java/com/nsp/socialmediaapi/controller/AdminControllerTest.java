package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.UserAlreadyBannedException;
import com.nsp.socialmediaapi.exception.ExceptionResponseBuilder;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.SelfBanNotAllowedException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.CommentNotFoundException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import com.nsp.socialmediaapi.service.Auth.JwtService;
import com.nsp.socialmediaapi.service.Comment.CommentService;
import com.nsp.socialmediaapi.service.Post.PostService;
import com.nsp.socialmediaapi.service.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ExceptionResponseBuilder.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private CommentService commentService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void deletePostFromUser_shouldDeletePostAndReturnNoContent() throws Exception{
        doNothing().when(postService).deletePostFromUser(1L);

        mockMvc.perform(delete("/api/v1/admin/posts/1"))
                .andExpect(status().isNoContent());

        verify(postService).deletePostFromUser(1L);
    }

    @Test
    void deletePostFromUser_shouldReturnNotFound_whenUserDoesNotExist() throws Exception{
        doThrow(new UserNotFoundException()).when(postService).deletePostFromUser(1L);

        mockMvc.perform(delete("/api/v1/admin/posts/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUserCommentFromPost_shouldDeletePostAndReturnNoContent() throws Exception{
        doNothing().when(commentService).deleteUserCommentFromPost(1L);

        mockMvc.perform(delete("/api/v1/admin/comments/1"))
                .andExpect(status().isNoContent());

        verify(commentService).deleteUserCommentFromPost(1L);
    }

    @Test
    void deleteUserCommentFromPost_shouldReturnNotFound_whenCommentDoesNotExist() throws Exception{
        doThrow(new CommentNotFoundException()).when(commentService).deleteUserCommentFromPost(1L);

        mockMvc.perform(delete("/api/v1/admin/comments/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void banUser_shouldBanUserAndReturnNoContent() throws Exception{
        doNothing().when(userService).banUser(1L);

        mockMvc.perform(patch("/api/v1/admin/users/1/ban"))
                .andExpect(status().isNoContent());

        verify(userService).banUser(1L);
    }

    @Test
    void banUser_shouldReturnNotFound_whenUserDoesNotExist() throws Exception{
        doThrow(new UserNotFoundException()).when(userService).banUser(1L);

        mockMvc.perform(patch("/api/v1/admin/users/1/ban"))
                .andExpect(status().isNotFound());
    }

    @Test
    void banUser_shouldReturnConflict_whenUserIsAlreadyBanned() throws Exception{
        doThrow(new UserAlreadyBannedException()).when(userService).banUser(1L);

        mockMvc.perform(patch("/api/v1/admin/users/1/ban"))
                .andExpect(status().isConflict());
    }

    @Test
    void banUser_shouldReturnBadRequest_whenLoggedUserTriesToBanHimself() throws Exception{
        doThrow(new SelfBanNotAllowedException()).when(userService).banUser(1L);

        mockMvc.perform(patch("/api/v1/admin/users/1/ban"))
                .andExpect(status().isBadRequest());
    }

}
