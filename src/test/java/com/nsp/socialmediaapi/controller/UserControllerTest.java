package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.exception.ExceptionResponseBuilder;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.service.Auth.JwtService;
import com.nsp.socialmediaapi.service.Post.PostService;
import com.nsp.socialmediaapi.service.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ExceptionResponseBuilder.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void showUserProfile_shouldCorrectlyReturnUserProfileDtoAndOkStatus() throws Exception {
        UserProfileDto result = UserProfileDto.builder()
                .id(1L)
                .username("username")
                .userDesc("desc")
                .starsCount(5L)
                .build();

        when(userService.findUserProfile(1L)).thenReturn(result);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.userDesc").value("desc"))
                .andExpect(jsonPath("$.starsCount").value(5L));

        verify(userService).findUserProfile(1L);
    }

    @Test
    void showUserProfile_shouldReturnNotFound_whenUserNotFound() throws Exception {
        doThrow(new UserNotFoundException()).when(userService).findUserProfile(1L);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void showUserPosts_shouldReturnPageOfUserPostsAndOkStatus() throws Exception {
        PageResponse<PostDtoSimple> response = PageResponse.<PostDtoSimple>builder()
                .content(List.of(PostDtoSimple.builder()
                        .id(1L)
                        .userDisplayDto(
                                UserDisplayDto.builder()
                                        .id(1L)
                                        .username("username")
                                        .build()
                        )
                        .title("tittle")
                        .build()))
                .page(0)
                .size(10)
                .totalPages(1)
                .build();

        when(postService.showUserPosts(1L, 0, 10)).thenReturn(response);

        mockMvc.perform(get("/api/v1/users/1/posts").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].title").value("tittle"))
                .andExpect(jsonPath("$.content[0].userDisplayDto.id").value(1L))
                .andExpect(jsonPath("$.content[0].userDisplayDto.username").value("username"));

        verify(postService).showUserPosts(1L, 0, 10);
    }

    @Test
    void showUserPosts_shouldReturnNotFound_whenUserNotFound() throws Exception {
        doThrow(new UserNotFoundException()).when(postService).showUserPosts(1L, 0, 10);

        mockMvc.perform(get("/api/v1/users/1/posts").param("page", "0").param("size", "10"))
                .andExpect(status().isNotFound());
    }

}
