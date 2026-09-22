package com.nsp.socialmediaapi.service;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.EmailAlreadyInUseException;
import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.UsernameAlreadyInUseException;
import com.nsp.socialmediaapi.exception.SecurityException.InvalidLoginCredentialsException;
import com.nsp.socialmediaapi.exception.SecurityException.UserDisabledException;
import com.nsp.socialmediaapi.model.dto.Login.LoginRequestDto;
import com.nsp.socialmediaapi.model.dto.Login.LoginResponseDto;
import com.nsp.socialmediaapi.model.dto.User.CreateUserRequest;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import com.nsp.socialmediaapi.service.Auth.AuthService;
import com.nsp.socialmediaapi.service.Auth.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtService jwtService;

    @InjectMocks
    AuthService authService;

    private final CreateUserRequest REGISTER_REQUEST_PREPARED = CreateUserRequest.builder()
            .username("username")
            .email("email@email.com")
            .userPassword("password")
            .build();

    private final LoginRequestDto LOGIN_REQUEST_PREPARED = LoginRequestDto.builder()
            .username("username")
            .userPassword("password")
            .build();

    private final LoginResponseDto LOGIN_RESPONSE_EXPECTED = LoginResponseDto.builder()
            .token("token")
            .refreshToken("refresh")
            .build();

    @Test
    public void registerUser_shouldRegisterUserSuccessfully() {

        when(passwordEncoder.encode("password")).thenReturn("password");

        authService.registerUser(REGISTER_REQUEST_PREPARED);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(captor.capture());

        User savedUser = captor.getValue();

        assertEquals(REGISTER_REQUEST_PREPARED.getUsername(), savedUser.getUsername());
        assertEquals(REGISTER_REQUEST_PREPARED.getEmail(), savedUser.getEmail());
        assertEquals(REGISTER_REQUEST_PREPARED.getUserPassword(), savedUser.getUserPassword());
        assertEquals(Role.USER, savedUser.getRole());
    }

    @Test
    public void registerUser_shouldThrowException_whenUsernameAlreadyInUse() {

        when(userRepository.existsByUsername(REGISTER_REQUEST_PREPARED.getUsername())).thenReturn(true);

        assertThrows(
          UsernameAlreadyInUseException.class,
                () -> authService.registerUser(REGISTER_REQUEST_PREPARED)
        );

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void registerUser_shouldThrowException_whenEmailAlreadyInUse() {

        when(userRepository.existsByEmail(REGISTER_REQUEST_PREPARED.getEmail())).thenReturn(true);

        assertThrows(
                EmailAlreadyInUseException.class,
                () -> authService.registerUser(REGISTER_REQUEST_PREPARED)
        );

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void login_shouldReturnToken_whenCredentialsAreValid() {

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.generateTokenResponse(authentication)).thenReturn(LOGIN_RESPONSE_EXPECTED);

        LoginResponseDto result = authService.login(LOGIN_REQUEST_PREPARED);

        assertEquals(LOGIN_RESPONSE_EXPECTED, result);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateTokenResponse(authentication);
    }

    @Test
    public void login_shouldReturnToken_withProvidedCredentials() {

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        when(jwtService.generateTokenResponse(authentication)).thenReturn(LOGIN_RESPONSE_EXPECTED);

        authService.login(LOGIN_REQUEST_PREPARED);

        ArgumentCaptor<UsernamePasswordAuthenticationToken> captor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);

        verify(authenticationManager).authenticate(captor.capture());

        UsernamePasswordAuthenticationToken token = captor.getValue();

        assertEquals(LOGIN_REQUEST_PREPARED.getUsername(), token.getPrincipal());
        assertEquals(LOGIN_REQUEST_PREPARED.getUserPassword(), token.getCredentials());
    }

    @Test
    public void login_shouldThrowException_whenUserIsDisabled() {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new UserDisabledException());

        assertThrows(
                UserDisabledException.class,
                () -> authService.login(LOGIN_REQUEST_PREPARED)
        );

        verify(jwtService, never()).generateTokenResponse(any());
    }

    @Test
    public void login_shouldThrowException_whenUserCredentialsAreInvalid() {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new InvalidLoginCredentialsException());

        assertThrows(
                InvalidLoginCredentialsException.class,
                () -> authService.login(LOGIN_REQUEST_PREPARED)
        );

        verify(jwtService, never()).generateTokenResponse(any());
    }

}
