package com.nsp.socialmediaapi.mapper;

import com.nsp.socialmediaapi.model.dto.User.UserDisplayDto;
import com.nsp.socialmediaapi.model.dto.User.UserProfileDto;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void mapperShouldCorrectlyMapUserDisplayDto() {
        User user = User.builder()
                .id(1L)
                .username("username")
                .userPassword("password")
                .role(Role.USER)
                .build();

        UserDisplayDto result = userMapper.mapToUserDisplayDto(user);

        assertNotNull(result);
        assertEquals(result.getId(), user.getId());
        assertEquals(result.getUsername(), user.getUsername());
    }

    @Test
    void mapperShouldCorrectlyMapUserProfileDto() {
        User user = User.builder()
                .id(1L)
                .username("username")
                .userPassword("password")
                .role(Role.USER)
                .userDesc("desc")
                .build();

        UserProfileDto result = userMapper.mapToUserProfileDto(user, 5L);

        assertNotNull(result);
        assertEquals(result.getId(), user.getId());
        assertEquals(result.getUsername(), user.getUsername());
        assertEquals(result.getUserDesc(), user.getUserDesc());
    }

}
