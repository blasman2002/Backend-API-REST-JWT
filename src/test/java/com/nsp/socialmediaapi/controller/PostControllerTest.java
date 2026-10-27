package com.nsp.socialmediaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsp.socialmediaapi.exception.ExceptionResponseBuilder;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.PostDoesNotBelongToUserException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.PostNotFoundException;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.request.Post.CreatePostRequest;
import com.nsp.socialmediaapi.service.Auth.JwtService;
import com.nsp.socialmediaapi.service.Post.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ExceptionResponseBuilder.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void createPost_shouldCorrectlyCreatePostAndReturnDto() throws Exception {
        CreatePostRequest request = CreatePostRequest.builder()
                .title("title")
                .content("content")
                .build();

        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("title")
                .content("content")
                .build();

        when(postService.createPost(any(CreatePostRequest.class))).thenReturn(postDto);

        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.content").value(request.getContent()));

        verify(postService).createPost(any(CreatePostRequest.class));
    }

    @Test
    void deleteOwnPost_shouldCorrectlyDeletePost() throws Exception {
        doNothing().when(postService).deleteOwnPost(1L);

        mockMvc.perform(delete("/api/v1/posts/1"))
                .andExpect(status().isNoContent());

        verify(postService).deleteOwnPost(1L);
    }

    @Test
    void deleteOwnPost_shouldThrowException_whenPostDoesNotBelongToLoggedUser() throws Exception {
        doThrow(new PostDoesNotBelongToUserException()).when(postService).deleteOwnPost(1L);

        mockMvc.perform(delete("/api/v1/posts/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllRecentPosts_shouldCorrectlyGetAllPosts() throws Exception {
        PageResponse<PostDtoSimple> response = PageResponse.<PostDtoSimple>builder()
                .content(List.of(PostDtoSimple.builder()
                        .id(1L)
                        .title("tittle")
                        .build()))
                .page(1)
                .size(10)
                .totalPages(1)
                .build();

        when(postService.showAllRecentPosts(1,10)).thenReturn(response);

        mockMvc.perform(get("/api/v1/posts").param("page", "1").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].title").value("tittle"));

        verify(postService).showAllRecentPosts(1, 10);
    }

    @Test
    void getPost_shouldCorrectlyGetPost() throws Exception {
        PostDto response = PostDto.builder()
                .id(1L)
                .title("tittle")
                .content("content")
                .build();

        when(postService.findPostDtoById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("tittle"))
                .andExpect(jsonPath("$.content").value("content"));

        verify(postService).findPostDtoById(1L);
    }

    @Test
    void getPost_shouldThrowException_whenPostDoesNotExists() throws Exception {
        doThrow(new PostNotFoundException()).when(postService).findPostDtoById(1L);

        mockMvc.perform(get("/api/v1/posts/1"))
                .andExpect(status().isNotFound());
    }

}
