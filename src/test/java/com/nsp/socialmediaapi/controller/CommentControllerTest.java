package com.nsp.socialmediaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsp.socialmediaapi.exception.ExceptionResponseBuilder;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.CommentDoesNotBelongToPostException;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.CommentDoesNotBelongToUserException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.PostNotFoundException;
import com.nsp.socialmediaapi.model.dto.Comment.CommentDto;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import com.nsp.socialmediaapi.model.request.Comment.CommentPostRequest;
import com.nsp.socialmediaapi.service.Auth.JwtService;
import com.nsp.socialmediaapi.service.Comment.CommentService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ExceptionResponseBuilder.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CommentService commentService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void commentPost_shouldReturnCreatedAndCommentDto() throws Exception {
        UserDisplayDto user = UserDisplayDto.builder()
                .id(1L)
                .username("username")
                .build();

        CommentPostRequest request = CommentPostRequest.builder()
                .content("content")
                .build();

        CommentDto commentDto = CommentDto.builder()
                .id(1L)
                .userDisplayDto(user)
                .content(request.getContent())
                .build();

        when(commentService.commentPost(eq(1L), any(CommentPostRequest.class))).thenReturn(commentDto);

        mockMvc.perform(post("/api/v1/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.content").value(commentDto.getContent()))
                .andExpect(jsonPath("$.userDisplayDto.username").value(commentDto.getUserDisplayDto().getUsername()))
                .andExpect(jsonPath("$.userDisplayDto.id").value(commentDto.getUserDisplayDto().getId()));

        verify(commentService).commentPost(eq(1L), any(CommentPostRequest.class));
    }

    @Test
    void commentPost_shouldReturnNotFound_whenPostDoesNotExists() throws Exception {
        CommentPostRequest request = CommentPostRequest.builder()
                .content("content")
                .build();

        doThrow(new PostNotFoundException()).when(commentService).commentPost(eq(1L), any(CommentPostRequest.class));

        mockMvc.perform(post("/api/v1/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCommentFromPost_shouldDeleteCommentAndReturnNoContent() throws Exception {
        doNothing().when(commentService).deleteCommentFromPost(1L,1L);

        mockMvc.perform(delete("/api/v1/posts/1/comments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCommentFromPost_shouldReturnBadRequest_whenCommentDoesNotBelongToPost() throws Exception {
        doThrow(new CommentDoesNotBelongToPostException()).when(commentService).deleteCommentFromPost(1L,1L);

        mockMvc.perform(delete("/api/v1/posts/1/comments/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCommentFromPost_shouldReturnBadRequest_whenCommentDoesNotBelongToLoggedUser() throws Exception {
        doThrow(new CommentDoesNotBelongToUserException()).when(commentService).deleteCommentFromPost(1L,1L);

        mockMvc.perform(delete("/api/v1/posts/1/comments/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCommentsFromPost_shouldReturnCommentPageFromPost() throws Exception {
        PageResponse<CommentDto> response = PageResponse.<CommentDto>builder()
                .content(List.of(CommentDto.builder()
                        .id(1L)
                        .userDisplayDto(
                                UserDisplayDto.builder()
                                        .id(1L)
                                        .username("username")
                                        .build()
                        )
                        .content("content")
                        .build()))
                .page(0)
                .size(10)
                .totalPages(1)
                .build();

        when(commentService.getAllCommentsFromPost(1L, 0, 10)).thenReturn(response);

        mockMvc.perform(get("/api/v1/posts/1/comments").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].content").value("content"))
                .andExpect(jsonPath("$.content[0].userDisplayDto.id").value(1L))
                .andExpect(jsonPath("$.content[0].userDisplayDto.username").value("username"));

        verify(commentService).getAllCommentsFromPost(1L, 0, 10);
    }

}
