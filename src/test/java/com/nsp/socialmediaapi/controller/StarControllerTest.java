package com.nsp.socialmediaapi.controller;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.StarAlreadyExistsException;
import com.nsp.socialmediaapi.exception.ExceptionResponseBuilder;
import com.nsp.socialmediaapi.exception.InvalidArgumentExceptions.SelfStarNotAllowedException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.StarNotFoundException;
import com.nsp.socialmediaapi.exception.NotFoundExceptions.UserNotFoundException;
import com.nsp.socialmediaapi.model.dto.Page.PageResponse;
import com.nsp.socialmediaapi.model.dto.Star.StarReceivedDto;
import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import com.nsp.socialmediaapi.service.Auth.JwtService;
import com.nsp.socialmediaapi.service.Star.StarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StarController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ExceptionResponseBuilder.class)
public class StarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StarService starService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void giveStarToUser_shouldCorrectlyGiveStarAndReturnCreatedAndMessage() throws Exception {
        doNothing().when(starService).giveStar(1L);

        mockMvc.perform(post("/api/v1/users/1/stars"))
                .andExpect(status().isCreated())
                .andExpect(content().string("star given"));

        verify(starService).giveStar(1L);
    }

    @Test
    void giveStarToUser_shouldReturnNotFound_whenStarredUserNotFound() throws Exception {
        doThrow(new UserNotFoundException()).when(starService).giveStar(1L);

        mockMvc.perform(post("/api/v1/users/1/stars"))
                .andExpect(status().isNotFound());
    }

    @Test
    void giveStarToUser_shouldReturnBadRequest_whenUserTriesToGiveStarToSelf() throws Exception {
        doThrow(new SelfStarNotAllowedException()).when(starService).giveStar(1L);

        mockMvc.perform(post("/api/v1/users/1/stars"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void giveStarToUser_shouldReturnConflict_whenStarAlreadyExists() throws Exception {
        doThrow(new StarAlreadyExistsException()).when(starService).giveStar(1L);

        mockMvc.perform(post("/api/v1/users/1/stars"))
                .andExpect(status().isConflict());
    }

    @Test
    void removeStarFromUser_shouldCorrectlyRemoveStarAndReturnNoContent() throws Exception {
        doNothing().when(starService).removeStar(1L);

        mockMvc.perform(delete("/api/v1/users/1/stars"))
                .andExpect(status().isNoContent());

        verify(starService).removeStar(1L);
    }

    @Test
    void removeStarFromUser_shouldReturnNotFound_whenStarNotFound() throws Exception {
        doThrow(new StarNotFoundException()).when(starService).removeStar(1L);

        mockMvc.perform(delete("/api/v1/users/1/stars"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getStarsReceivedFromUser_shouldCorrectlyReturnStarPage() throws Exception {
        PageResponse<StarReceivedDto> response = PageResponse.<StarReceivedDto>builder()
                .content(List.of(StarReceivedDto.builder()
                        .userGiver(
                                UserDisplayDto.builder()
                                        .id(1L)
                                        .username("username")
                                        .build()
                        )
                        .build()))
                .page(0)
                .size(10)
                .totalPages(1)
                .build();

        when(starService.getUserStars(1L, 0, 10)).thenReturn(response);

        mockMvc.perform(get("/api/v1/users/1/stars").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].userGiver.id").value(1L))
                .andExpect(jsonPath("$.content[0].userGiver.username").value("username"));

        verify(starService).getUserStars(1L, 0, 10);
    }

}
