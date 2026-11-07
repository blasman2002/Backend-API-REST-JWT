package com.nsp.socialmediaapi.mapper;

import com.nsp.socialmediaapi.model.dto.Post.PostDto;
import com.nsp.socialmediaapi.model.dto.Post.PostDtoSimple;
import com.nsp.socialmediaapi.model.enm.Role;
import com.nsp.socialmediaapi.model.entity.Post;
import com.nsp.socialmediaapi.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class PostMapperTest {

    @Autowired
    private PostMapper postMapper;

    @Test
    void mapperShouldCorrectlyMapToPostDto() {
        User user = User.builder()
                .id(1L)
                .username("username")
                .userPassword("password")
                .role(Role.USER)
                .build();

        Post post = Post.builder()
                .id(1L)
                .user(user)
                .title("tittle")
                .content("content")
                .build();

        PostDto result = postMapper.mapToPostDto(post);

        assertEquals(result.getTitle(), post.getTitle());
        assertEquals(result.getContent(), post.getContent());
        assertEquals(result.getId(), post.getId());

        assertNotNull(result.getUserDisplayDto());
        assertEquals(result.getUserDisplayDto().getId(), post.getUser().getId());
        assertEquals(result.getUserDisplayDto().getUsername(), post.getUser().getUsername());
    }

    @Test
    void mapperShouldCorrectlyMapToPostDtoSimple() {
        User user = User.builder()
                .id(1L)
                .username("username")
                .userPassword("password")
                .role(Role.USER)
                .build();

        Post post = Post.builder()
                .id(1L)
                .user(user)
                .title("tittle")
                .content("content")
                .build();

        PostDtoSimple result = postMapper.mapToPostDtoSimple(post);

        assertEquals(result.getTitle(), post.getTitle());
        assertEquals(result.getId(), post.getId());

        assertNotNull(result.getUserDisplayDto());
        assertEquals(result.getUserDisplayDto().getId(), post.getUser().getId());
        assertEquals(result.getUserDisplayDto().getUsername(), post.getUser().getUsername());
    }

}
