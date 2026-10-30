package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.LikeAlreadyExistsException;
import com.nsp.socialmediaapi.exception.ExceptionResponseBuilder;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.LikeNotFoundException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.PostNotFoundException;
import com.nsp.socialmediaapi.service.Auth.JwtService;
import com.nsp.socialmediaapi.service.Like.LikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ExceptionResponseBuilder.class)
public class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LikeService likeService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void giveLikeToPost_shouldCreateLikeAndReturnCreated() throws Exception {
        doNothing().when(likeService).giveLikeToPost(1L);

        mockMvc.perform(post("/api/v1/posts/1/likes"))
                .andExpect(status().isCreated());

        verify(likeService).giveLikeToPost(1L);
    }

    @Test
    void giveLikeToPost_shouldReturnConflict_whenUserLikeAlreadyExists() throws Exception {
        doThrow(new LikeAlreadyExistsException()).when(likeService).giveLikeToPost(1L);

        mockMvc.perform(post("/api/v1/posts/1/likes"))
                .andExpect(status().isConflict());
    }

    @Test
    void giveLikeToPost_shouldReturnNotFound_whenPostDoesNotExists() throws Exception {
        doThrow(new PostNotFoundException()).when(likeService).giveLikeToPost(1L);

        mockMvc.perform(post("/api/v1/posts/1/likes"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteLike_shouldDeleteLikeAndReturnNoContent() throws Exception {
        doNothing().when(likeService).removeLikeFromPost(1L);

        mockMvc.perform(delete("/api/v1/posts/1/likes"))
                .andExpect(status().isNoContent());

        verify(likeService).removeLikeFromPost(1L);
    }

    @Test
    void deleteLike_shouldReturnNotFound_whenLikeDoesNotExists() throws Exception {
        doThrow(new LikeNotFoundException()).when(likeService).removeLikeFromPost(1L);

        mockMvc.perform(delete("/api/v1/posts/1/likes"))
                .andExpect(status().isNotFound());
    }

}
