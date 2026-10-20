package com.nsp.socialmediaapi.mapper;

import com.nsp.socialmediaapi.model.dto.Star.StarReceivedDto;
import com.nsp.socialmediaapi.model.entity.Star;
import com.nsp.socialmediaapi.model.entity.StarId;
import com.nsp.socialmediaapi.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StarMapperTest {

    @Autowired
    private StarMapper starMapper;

    @Test
    void mapperShouldCorrectlyMapStarReceivedDto() {
        User user1 = User.builder()
                .id(1L)
                .username("username1")
                .build();

        User user2 = User.builder()
                .id(2L)
                .username("username2")
                .build();

        StarId starId = StarId.builder()
                .user1id(user1.getId())
                .user2id(user2.getId())
                .build();

        Star star = Star.builder()
                .starId(starId)
                .user1(user1)
                .user2(user2)
                .build();

        StarReceivedDto result = starMapper.mapToStarReceivedDto(star);

        assertNotNull(result.getUserGiver());
        assertEquals(result.getUserGiver().getId(), star.getUser1().getId());
        assertEquals(result.getCreatedAt(), star.getCreatedAt());
    }

}
