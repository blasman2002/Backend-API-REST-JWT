package com.nsp.socialmediaapi.service;

import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.EmailAlreadyInUseException;
import com.nsp.socialmediaapi.exception.AlreadyExistsExceptions.UsernameAlreadyInUseException;
import com.nsp.socialmediaapi.model.dto.User.CreateUserRequest;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import com.nsp.socialmediaapi.service.Auth.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @InjectMocks
    AuthService authService;

    private final CreateUserRequest REQUEST_PREPARED = CreateUserRequest.builder()
            .username("username")
            .email("email@email.com")
            .userPassword("password")
            .build();

    @Test
    public void registerUser_shouldRegisterUserSuccessfully() {

        when(passwordEncoder.encode("password")).thenReturn("password");

        authService.registerUser(REQUEST_PREPARED);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(captor.capture());

        User savedUser = captor.getValue();

        assertEquals(REQUEST_PREPARED.getUsername(), savedUser.getUsername());
        assertEquals(REQUEST_PREPARED.getEmail(), savedUser.getEmail());
        assertEquals(REQUEST_PREPARED.getUserPassword(), savedUser.getUserPassword());
        assertEquals(Role.USER, savedUser.getRole());
    }

    @Test
    public void registerUser_shouldThrowException_whenUsernameAlreadyInUse() {

        when(userRepository.existsByUsername(REQUEST_PREPARED.getUsername())).thenReturn(true);

        assertThrows(
          UsernameAlreadyInUseException.class,
                () -> authService.registerUser(REQUEST_PREPARED)
        );

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void registerUser_shouldThrowException_whenEmailAlreadyInUse() {

        when(userRepository.existsByEmail(REQUEST_PREPARED.getEmail())).thenReturn(true);

        assertThrows(
                EmailAlreadyInUseException.class,
                () -> authService.registerUser(REQUEST_PREPARED)
        );

        verify(userRepository, never()).save(any(User.class));
    }

}
