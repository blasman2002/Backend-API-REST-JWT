package com.nsp.socialmediaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.EmailAlreadyInUseException;
import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.UsernameAlreadyInUseException;
import com.nsp.socialmediaapi.exception.ExceptionResponseBuilder;
import com.nsp.socialmediaapi.exception.SecurityException.InvalidLoginCredentialsException;
import com.nsp.socialmediaapi.exception.SecurityException.UserDisabledException;
import com.nsp.socialmediaapi.model.dto.Login.LoginResponseDto;
import com.nsp.socialmediaapi.model.request.Login.LoginRequestDto;
import com.nsp.socialmediaapi.model.request.Login.RefreshTokenRequest;
import com.nsp.socialmediaapi.model.request.User.CreateUserRequest;
import com.nsp.socialmediaapi.service.Auth.AuthService;
import com.nsp.socialmediaapi.service.Auth.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ExceptionResponseBuilder.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtService jwtService;

    public String PREPARED_JSON = """
            {
                "username": "username",
                "email": "email@email.com",
                "userPassword": "password"
            }
            """;

    public LoginRequestDto PREPARED_LOGIN_REQUEST = LoginRequestDto.builder()
            .username("username")
            .userPassword("password")
            .build();

    @Test
    void registerUser_shouldReturnCreated() throws Exception {
        doNothing().when(authService).registerUser(any(CreateUserRequest.class));

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(PREPARED_JSON))
                .andExpect(status().isCreated());

        verify(authService).registerUser(any(CreateUserRequest.class));

    }

    @Test
    void registerUser_shouldReturnConflict_whenUsernameAlreadyInUse() throws Exception {
        doThrow(new UsernameAlreadyInUseException()).when(authService).registerUser(any(CreateUserRequest.class));

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(PREPARED_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void registerUser_shouldReturnConflict_whenEmailAlreadyInUse() throws Exception {
        doThrow(new EmailAlreadyInUseException()).when(authService).registerUser(any(CreateUserRequest.class));

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PREPARED_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void login_shouldReturnTokenResponse() throws Exception {
        LoginResponseDto response = LoginResponseDto.builder()
                .token("login-token")
                .refreshToken("refresh-token")
                .build();

        when(authService.login(any(LoginRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PREPARED_LOGIN_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("login-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"));

        verify(authService).login(any(LoginRequestDto.class));
    }

    @Test
    void login_shouldReturnForbidden_whenUserIsDisabled() throws Exception {
        doThrow(new UserDisabledException()).when(authService).login(any(LoginRequestDto.class));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PREPARED_LOGIN_REQUEST)))
                .andExpect(status().isForbidden());
    }

    @Test
    void login_shouldReturnUnauthorized_whenCredentialsAreInvalid() throws Exception {
        doThrow(new InvalidLoginCredentialsException()).when(authService).login(any(LoginRequestDto.class));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PREPARED_LOGIN_REQUEST)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void refreshToken_shouldReturnNewToken() throws Exception {
        RefreshTokenRequest request = new RefreshTokenRequest("refresh-token");

        LoginResponseDto response = LoginResponseDto.builder()
                .token("login-token")
                .refreshToken("refresh-token")
                .build();

        when(authService.refreshToken(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("login-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"));

        verify(authService).refreshToken(any(RefreshTokenRequest.class));
    }

    @Test
    void refreshToken_shouldReturnUnauthorized_whenTokenIsNotValid() throws Exception {
        RefreshTokenRequest request = new RefreshTokenRequest("refresh-token");

        doThrow(new InvalidLoginCredentialsException()).when(authService).refreshToken(any(RefreshTokenRequest.class));

        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

}
