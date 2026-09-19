package com.nsp.socialmediaapi.service;

import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.entity.User;
import com.nsp.socialmediaapi.model.repository.UserRepository;
import com.nsp.socialmediaapi.service.User.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    public User USER_PREPARED = User.builder()
            .id(32L)
            .username("username2000")
            .userPassword("pass5000")
            .userDesc("bla bla bla")
            .role(Role.USER)
            .build();

    @Test
    void findById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER_PREPARED));

        User userDB = userService.findById(1L);

        assertEquals(32, userDB.getId());
    }

}
